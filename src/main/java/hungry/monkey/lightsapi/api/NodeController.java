package api;

import auth.AuthenticationService;
import auth.ClientDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Slf4j
@RestController
public class NodeController {
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("/setColor")
    @ResponseBody
    public Serializable sendColorRequest(
            @RequestParam(value = "room", defaultValue = "office") String roomName,
            @RequestParam(value = "rgb", defaultValue = "0,0,0") String rgb,
            @RequestParam(value = "clientId", defaultValue = "null") String clientId,
            @RequestParam(value = "clientSecret", defaultValue = "null") String clientSecret
    ) {
        if (authenticationService.isClientRequestValid(clientId, clientSecret)) {
            authenticationService.processClientRequest(clientId, clientSecret);
            ClientDataResponse clientDataResponse = new ClientDataResponse();
            clientDataResponse.setClientId(clientId);
            clientDataResponse.setClientData(authenticationService.getClientData(clientId));
            clientDataResponse.setNotes("request successful");

            return clientDataResponse;
        } else {
            return "request is invalid";
        }
    }

    @RequestMapping("/getAuthData")
    @ResponseBody
    public ClientDataResponse getAuthData() {
        log.info("Adding new client");
        return authenticationService.addNewClient();
    }
}
