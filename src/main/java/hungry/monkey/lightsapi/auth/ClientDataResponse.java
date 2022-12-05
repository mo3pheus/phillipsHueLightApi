package auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ClientDataResponse implements Serializable {
    @Getter
    @Setter
    private ClientData clientData;

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String notes;

    @Override
    public String toString() {
        return "clientId:" + clientId + ", clientData:" + clientData + ", notes:" + notes;
    }
}
