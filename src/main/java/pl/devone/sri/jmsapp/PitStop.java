package pl.devone.sri.jmsapp;

import lombok.extern.log4j.Log4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.racer.dto.RacerData;

@Log4j
@Component
public class PitStop {

    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
//    @SendTo("outbound.topic")
    public void receiveCurrentRacerData(RacerData racerData) {
        log.info("receiveCurrentRacerData <" + racerData + ">");
    }


    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
//    @SendTo("outbound.topic")
    public void reciveAndPrint(RacerData racerData) {
        log.info("reciveAndPrint <" + racerData + ">");
    }

}
