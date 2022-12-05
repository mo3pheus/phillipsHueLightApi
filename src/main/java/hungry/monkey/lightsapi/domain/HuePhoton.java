package hungry.monkey.lightsapi.hue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class HuePhoton implements Serializable {
    @Getter
    @Setter
    private Boolean on;

    @Getter
    @Setter
    private String xy;

    @Getter
    @Setter
    private Integer brightness;

    @Getter
    @Setter
    private Integer hue;

    @Getter
    @Setter
    private Integer saturation;

    public HuePhoton(String xy, Integer hue, Integer saturation, Integer brightness, Boolean on) {
        this.xy = xy;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.on = on;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String objectString = "";
        try {
            objectString = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Could not get string format for object.", jsonProcessingException);
        }
        return objectString;
    }
}
