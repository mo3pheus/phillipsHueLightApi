package hungry.monkey.lightsapi.api;

import hungry.monkey.lightsapi.auth.AuthenticationService;
import hungry.monkey.lightsapi.auth.ClientData;
import hungry.monkey.lightsapi.auth.ClientDataResponse;
import hungry.monkey.lightsapi.domain.ClientFrequency;
import hungry.monkey.lightsapi.domain.HuePhoton;
import hungry.monkey.lightsapi.mqtt.MessageRelay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@RestController
public class NodeController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageRelay messageRelay;

    @RequestMapping("/setState")
    @ResponseBody
    public Serializable sendStateRequest(
            @RequestParam(value = "clientId", defaultValue = "null") String clientId,
            @RequestParam(value = "clientSecret", defaultValue = "null") String clientSecret,
            @RequestParam(value = "xy", defaultValue = "0.5425,0.4196") String xy,
            @RequestParam(value = "brightness", defaultValue = "42") Integer brightness,
            @RequestParam(value = "hue", defaultValue = "12750") Integer hue,
            @RequestParam(value = "saturation", defaultValue = "200") Integer saturation,
            @RequestParam(value = "on", defaultValue = "true") Boolean on
    ) {
        if (authenticationService.isClientRequestValid(clientId, clientSecret)) {
            authenticationService.addValidClientRequest(clientId);
            ClientDataResponse clientDataResponse = new ClientDataResponse();
            clientDataResponse.setClientId(clientId);
            clientDataResponse.setClientData(authenticationService.getClientData(clientId));
            clientDataResponse.setNotes("request successful");

            String payloadString = (new HuePhoton(
                    HuePhoton.convertToXyFloat(xy),
                    hue,
                    saturation,
                    brightness,
                    on)).toString();
            messageRelay.sendMessage(payloadString.getBytes());

            return clientDataResponse;
        } else {
            return "request is invalid";
        }
    }

    @GetMapping("/getAllClients")
    @ResponseBody
    public ClientFrequency getAllClients() {
        ClientFrequency clientFrequency = new ClientFrequency();

        Map<String, ClientData> clientDataMap = authenticationService.getClientDataMap();
        for (String clientId : clientDataMap.keySet()) {
            ClientData clientData = clientDataMap.get(clientId);
            clientFrequency.addClient(clientId, clientData.getNumRequests());
        }

        return clientFrequency;
    }

    @RequestMapping("/getAuthData")
    @ResponseBody
    public ClientDataResponse getAuthData() {
        log.info("Adding new client");
        return authenticationService.addNewClient();
    }
}
