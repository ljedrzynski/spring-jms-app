package pl.devone.sri.jmsapp.pitcrew;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.Message;

import javax.jms.Queue;

@Slf4j
@Component
public class PitCrew {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public PitCrew(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.TEMP_QUEUE, containerFactory = "queueListenerFactory")
    public void onMessage(Message message, MessageHeaders headers) {
        log.info("PitCrew.onMessage => {}", message);
        if (message.getType().equals(Message.Type.REQUEST_PIT_STOP)) {
            Message response = new Message();
            response.setType(Message.Type.INFO);
            response.setContent("Ok");
            jmsTemplate.convertAndSend((Queue) headers.get(JmsConfig.REPLY_TO), response);
        }
    }
}
