package hungry.monkey.lightsapi;

import hungry.monkey.lightsapi.domain.HuePhoton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHuePhoton {
    HuePhoton huePhoton;

    @Before
    public void setUp() {
        Float[] xy = new Float[2];
        xy[0] = 0.5425f;
        xy[1] = 0.4196f;
        huePhoton = new HuePhoton(xy, 12750, 200, 42, true);
    }

    @Test
    public void testStringRep() {
        String hueString = huePhoton.toString();
        String expectedString = "{\"on\":true,\"xy\":[0.5425,0.4196],\"bri\":42,\"hue\":12750,\"sat\":200}";
        Assert.assertEquals(expectedString, hueString);
        System.out.println(hueString);
    }
}
