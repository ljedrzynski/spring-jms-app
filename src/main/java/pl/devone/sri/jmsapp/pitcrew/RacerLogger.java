package pl.devone.sri.jmsapp.pitcrew;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.RacerData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class RacerLogger {

    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
    public void log(RacerData racerData) {
        try {
            Files.write(
                    Paths.get("racer.log"),
                    (racerData.toString() + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
