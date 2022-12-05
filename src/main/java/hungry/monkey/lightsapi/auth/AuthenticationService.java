package auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
    public static Integer MAX_NUM_REQUESTS = 50;

    private Map<String, ClientData> clientDataMap = new HashMap<>();

    public ClientData getClientData(String clientId) {
        return clientDataMap.get(clientId);
    }

    @PostConstruct
    private void markBeginning() {
        log.info("Authentication Service created and started." + clientDataMap.size());
    }

    public ClientDataResponse addNewClient() {
        String clientId = UUID.randomUUID().toString();
        String clientSecretOriginal = UUID.randomUUID().toString();
        String clientSecret = Base64.encodeBase64String(clientSecretOriginal.getBytes());
        clientSecret = Base64.encodeBase64String(clientSecret.getBytes());

        ClientData clientData = new ClientData();
        clientData.setSecret(clientSecretOriginal);
        clientData.setNumRequests(0);

        clientDataMap.put(clientId, clientData);

        ClientDataResponse clientDataResponse = new ClientDataResponse();
        clientDataResponse.setClientId(clientId);
        clientData.setSecret(clientSecret);
        clientDataResponse.setClientData(clientData);

        log.info("Added new client " + clientDataResponse);

        return clientDataResponse;
    }

    public Boolean isClientRequestValid(String clientId, String clientSecret) {
        if (!clientDataMap.containsKey(clientId)) {
            return false;
        } else {
            ClientData clientData = clientDataMap.get(clientId);

            if (clientData.getSecret().equals(Base64.decodeBase64(clientSecret))
                    && clientData.getNumRequests() <= MAX_NUM_REQUESTS) {
                return true;
            } else return false;
        }
    }

    public void processClientRequest(String clientId, String clientSecret) {
        if (!isClientRequestValid(clientId, clientSecret)) {
            return;
        }

        ClientData clientData = clientDataMap.get(clientId);
        clientData.incrementNumRequests();

        clientDataMap.remove(clientId);
        clientDataMap.put(clientId, clientData);

        log.info("Incremented request for clientId = " + clientId);
    }
}
