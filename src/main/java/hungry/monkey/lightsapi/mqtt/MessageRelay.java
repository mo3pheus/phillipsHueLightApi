package hungry.monkey.lightsapi.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageRelay {
    private String hostName;
    private String port;
    private String topicName;
    private IMqttClient mqttClient;

    public MessageRelay(@Value("${mqtt.host}") String hostName,
                        @Value("${mqtt.port}") String port,
                        @Value("${topic.name}") String topicName) {
        try {
            this.hostName = hostName;
            this.port = port;
            this.topicName = topicName;

            String mqttConnectString = "tcp://" + hostName + ":" + port;
            mqttClient = new MqttClient(mqttConnectString, MqttClient.generateClientId());

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setMaxInflight(20);

            mqttClient.connect(mqttConnectOptions);
            log.info("Mqtt Client has been connected.");
        } catch (MqttException m) {
            log.error("Could not create messageRelay", m);
        }
    }

    public void sendMessage(byte[] messagePayload) {
        try {
            mqttClient.publish(topicName, new MqttMessage(messagePayload));
            log.info("published message to " + topicName + " message: " + new String(messagePayload));
        } catch (MqttException e) {
            log.error("Could not publish to mqtt", e);
        }
    }
}
