package hungry.monkey.lightsapi;

import hungry.monkey.lightsapi.domain.HuePhoton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHuePhoton {
    HuePhoton huePhoton;

    @Before
    public void setUp() {
        huePhoton = new HuePhoton("[0.5425,0.4196]", 12750, 200, 42, true);
    }

    @Test
    public void testStringRep() {
        String hueString = huePhoton.toString();
        String expectedString = "{\"on\":true,\"xy\":\"[0.5425,0.4196]\",\"brightness\":42,\"hue\":12750,\"saturation\":200}";
        Assert.assertEquals(expectedString, hueString);
        System.out.println(hueString);
    }
}
