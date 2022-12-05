package auth;

import lombok.Getter;
import lombok.Setter;

public class ClientData {
    @Getter
    @Setter
    String secret;

    @Getter
    @Setter
    Integer numRequests;

    @Override
    public String toString() {
        return "secret:" + secret + ", numRequests:" + numRequests;
    }

    public void incrementNumRequests() {
        numRequests++;
    }
}
