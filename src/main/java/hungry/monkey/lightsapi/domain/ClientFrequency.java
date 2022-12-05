package hungry.monkey.lightsapi.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ClientFrequency implements Serializable {

    @Getter
    @Setter
    private Map<String, Integer> clientFrequencyMap;

    public ClientFrequency() {
        this.clientFrequencyMap = new HashMap<>();
    }

    public void addClient(String client, Integer frequency) {
        this.clientFrequencyMap.put(client, frequency);
    }
}
