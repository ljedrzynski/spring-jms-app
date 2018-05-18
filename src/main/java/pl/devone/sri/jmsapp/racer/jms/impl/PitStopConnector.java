package pl.devone.sri.jmsapp.racer.jms.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.racer.dto.RacerData;
import pl.devone.sri.jmsapp.racer.jms.IRacerConnector;
import pl.devone.sri.jmsapp.racer.jms.IRacerObserver;

import java.util.Optional;

@Log4j
@Component
@Scope("prototype")
public class PitStopConnector implements IRacerConnector, IRacerObserver {

    private final JmsTemplate jmsTemplate;
    private RacerData racerData;

    @Autowired
    public PitStopConnector(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void update(RacerData racer) {
        this.racerData = racer;
    }

    @Scheduled(fixedRate = 15000)
    public void sendData() {
        Optional.ofNullable(racerData).ifPresent(data -> jmsTemplate.convertAndSend(JmsConfig.RACER_TOPIC, data));
    }
}
