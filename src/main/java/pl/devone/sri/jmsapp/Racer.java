package pl.devone.sri.jmsapp;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.RacerData;

import java.time.LocalDateTime;

@Log4j
@Component
public class Racer {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public Racer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 5000)
    private void sendCurrentData() {
        RacerData racerData = new RacerData();
        racerData.setTime(LocalDateTime.now());
        log.info("Sending data");
        jmsTemplate.convertAndSend(JmsConfig.RACER_TOPIC, "yey");
    }
}
