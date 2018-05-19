package pl.devone.sri.jmsapp.pitcrew;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.config.JmsConfig;
import pl.devone.sri.jmsapp.dto.Message;
import pl.devone.sri.jmsapp.dto.RacerData;

@Component
public class RacerMonitor {

    @JmsListener(destination = JmsConfig.RACER_TOPIC, containerFactory = "topicListenerFactory")
    @SendTo(JmsConfig.MESSAGE_ROUTER_QUEUE)
    public Message monitor(RacerData racerData) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean failure = false;

        if (racerData != null) {
            double engineTemperature = racerData.getEngineTemperature();
            if (!between(engineTemperature, 90, 120)) {
                stringBuilder.append("Engine temperature is not in correct range.\n");
            }
            if (between(engineTemperature, 120, 200)) {
                stringBuilder.append(String.format("Engine failure detected. Current temperature :%s.\n", engineTemperature));
                failure = true;
            }

            double oilPressure = racerData.getOilPressure();
            if (!between(oilPressure, 70, 150)) {
                stringBuilder.append("Oil pressure is not in correct range.\n");
            }
            if (between(oilPressure, 150, 300) || between(oilPressure, 0, 150)) {
                stringBuilder.append(String.format("Engine failure detected. Current oil pressure :%s.\n", engineTemperature));
                failure = true;
            }

            double[] tirePressure = racerData.getTirePressure();
            if (tirePressure != null) {
                for (int i = 0; i < tirePressure.length; i++) {
                    if (tirePressure[i] == 0) {
                        stringBuilder.append(String.format("Punctured %s tire.\n", i));
                        failure = true;
                    }
                    if (!between(tirePressure[i], 1.2, 1.5)) {
                        stringBuilder.append(String.format("Pressure in %s tire is not correct.\n", i));
                    }
                }
            }
        }

        return new Message(failure ? Message.Type.FAILURE : Message.Type.WARNING, stringBuilder.toString());
    }

    private boolean between(double i, double minValueInclusive, double maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }
}
