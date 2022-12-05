package hungry.monkey.lightsapi.auth;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AuthenticationService {
    @Value("${client.request.cap}")
    private Integer maxNumRequests;

    @Getter
    private Map<String, ClientData> clientDataMap = new HashMap<>();

    public ClientData getClientData(String clientId) {
        return clientDataMap.get(clientId);
    }

    public ClientDataResponse addNewClient() {
        String clientId = UUID.randomUUID().toString();
        String clientSecretOriginal = UUID.randomUUID().toString();
        String clientSecret = Base64.encodeBase64String(clientSecretOriginal.getBytes());
        clientSecret = Base64.encodeBase64String(clientSecret.getBytes());

        ClientData clientDataRecord = new ClientData();
        clientDataRecord.setSecret(clientSecretOriginal);
        clientDataRecord.setNumRequests(0);

        clientDataMap.put(clientId, clientDataRecord);

        ClientDataResponse clientDataResponse = new ClientDataResponse();
        clientDataResponse.setClientId(clientId);
        ClientData clientData = new ClientData();
        clientData.setNumRequests(0);
        clientData.setSecret(clientSecret);
        clientDataResponse.setClientData(clientData);

        log.debug("Added new client " + clientId);

        return clientDataResponse;
    }

    public Boolean isClientRequestValid(String clientId, String clientSecret) {
        if (!clientDataMap.containsKey(clientId)) {
            log.warn("ClientDataMap does not contain key" + clientId + " request is invalid");
            return false;
        } else {
            ClientData clientData = clientDataMap.get(clientId);
            String decodedClientSecret = new String(Base64.decodeBase64(clientSecret.getBytes()));
            if (clientData.getSecret().equals(decodedClientSecret)
                    && clientData.getNumRequests() < maxNumRequests) {
                log.info("ClientId = " + clientId + " valid request");
                return true;
            } else return false;
        }
    }

    public void addValidClientRequest(String clientId) {
        ClientData clientData = clientDataMap.get(clientId);
        clientData.incrementNumRequests();

        clientDataMap.remove(clientId);
        clientDataMap.put(clientId, clientData);

        log.info("Incremented request for clientId = " + clientId);
    }

    @Scheduled(fixedRate = 1000)
    public void cleanUpMaxedOutClients() {
        List<String> defaultingClients = new ArrayList<>();
        for (String clientId : clientDataMap.keySet()) {
            ClientData clientData = clientDataMap.get(clientId);
            if (clientData.getNumRequests() == maxNumRequests) {
                defaultingClients.add(clientId);
            }
        }

        for (String clientId : defaultingClients) {
            log.info("Removing client due to max requests reached. ClientId = " + clientId);
            clientDataMap.remove(clientId);
        }
    }
}
