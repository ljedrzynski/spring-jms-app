package pl.devone.sri.jmsapp.pitcrew;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.Message;

@Slf4j
@Component
public class PitCrew {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public PitCrew(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.PIT_CREW_QUEUE, containerFactory = "queueListenerFactory")
    public void onMessage(Message message) {
        log.info("PitCrew.onMessage=>{}", message);
        if (message.getType().equals(Message.Type.REQUEST_PIT_STOP)) {
            jmsTemplate.convertAndSend(JmsConfig.RACER_QUEUE, new Message(Message.Type.INFO, "OK"));
        }
    }
}
