package pl.devone.sri.jmsapp.racer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.Message;
import pl.devone.sri.jmsapp.dto.RacerData;

import java.util.Date;

@Slf4j
@Data
@Component
@Scope("prototype")
public class Racer {
    private final JmsMessagingTemplate jmsTemplate;
    private int racerId;
    private double engineTemperature;
    private double[] tirePressure;
    private double oilPressure;
    private double speed;

    @Autowired
    public Racer(JmsMessagingTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.RACER_QUEUE, containerFactory = "queueListenerFactory")
    public void onMessage(Message message) {
        log.info("Racer{}.onMessage => {}", racerId, message);
    }

    @Scheduled(fixedRate = 15000)
    public void notifyCrew() {
        RacerData racerData = new RacerData();
        racerData.setRacerId(racerId);
        racerData.setEngineTemperature(engineTemperature);
        racerData.setOilPressure(oilPressure);
        racerData.setSpeed(speed);
        racerData.setTirePressure(tirePressure);
        racerData.setTime(new Date());
        jmsTemplate.convertAndSend(JmsConfig.RACER_TOPIC, racerData);
    }

    @Scheduled(fixedRate = 30000)
    public void requestPitStop() {
        Message response = jmsTemplate.convertSendAndReceive(JmsConfig.TEMP_QUEUE, new Message(Message.Type.REQUEST_PIT_STOP, null, null), Message.class);
        log.info("Racer{}.requestPitStop => response: {}", racerId, response);
    }
}
