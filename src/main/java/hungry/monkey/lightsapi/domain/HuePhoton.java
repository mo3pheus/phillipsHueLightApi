package hungry.monkey.lightsapi.domain;

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
    private Float[] xy;

    @Getter
    @Setter
    private Integer bri;

    @Getter
    @Setter
    private Integer hue;

    @Getter
    @Setter
    private Integer sat;

    public HuePhoton(Float[] xy, Integer hue, Integer sat, Integer brightness, Boolean on) {
        this.xy = xy;
        this.hue = hue;
        this.sat = sat;
        this.bri = brightness;
        this.on = on;
    }

    public HuePhoton() {

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

    public static Float[] convertToXyFloat(String xy){
        String[] xyArr = xy.split(",");
        Float[] xyFloat = new Float[2];
        xyFloat[0]=Float.parseFloat(xyArr[0]);
        xyFloat[1]=Float.parseFloat(xyArr[1]);

        return xyFloat;
    }
}
