package pl.devone.sri.jmsapp.pitcrew;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.Message;

@Slf4j
@Component
public class MessageRouter {
    private final JmsTemplate jmsTemplate;

    public MessageRouter(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.MESSAGE_ROUTER_QUEUE, containerFactory = "queueListenerFactory")
    public void onMessage(Message message) {
        log.info("MessageRouter.onMessage=>{}", message);
        jmsTemplate.convertAndSend(JmsConfig.RACER_QUEUE, message);
        if (message.getType().equals(Message.Type.FAILURE)) {
            jmsTemplate.convertAndSend(JmsConfig.PIT_CREW_QUEUE, message);
        }
    }
}
