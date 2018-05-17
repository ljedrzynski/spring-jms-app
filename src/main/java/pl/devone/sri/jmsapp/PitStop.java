package pl.devone.sri.jmsapp;

import lombok.extern.log4j.Log4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;

@Log4j
@Component
public class PitStop {

    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
//    @SendTo("outbound.topic")
    public void receiveCurrentRacerData(String racerData) {
        log.info("receiveCurrentRacerData <" + racerData + ">");
    }


    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
//    @SendTo("outbound.topic")
    public void reciveAndPrint(String racerData) {
        log.info("reciveAndPrint <" + racerData + ">");
    }

}
