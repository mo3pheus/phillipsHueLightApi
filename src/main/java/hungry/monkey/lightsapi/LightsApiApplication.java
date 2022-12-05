package hungry.monkey.lightsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LightsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightsApiApplication.class, args);
    }
}
