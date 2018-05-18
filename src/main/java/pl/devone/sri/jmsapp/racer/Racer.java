package pl.devone.sri.jmsapp.racer;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.devone.sri.jmsapp.racer.dto.RacerData;
import pl.devone.sri.jmsapp.racer.jms.IRacerObserver;

import java.util.Date;
import java.util.List;

@Log4j
@Data
@Component
@Scope("prototype")
public class Racer {
    private final List<IRacerObserver> observers;
    private int racerId;
    private double engineTemperature;
    private double[] tirePressure;
    private double oilPressure;
    private double speed;

    @Autowired
    public Racer(List<IRacerObserver> observers) {
        this.observers = observers;
    }

    //    @LogIt
    @Scheduled(fixedRate = 5000)
    public void notifyObservers() {
        RacerData racerData = new RacerData();
        racerData.setRacerId(racerId);
        racerData.setEngineTemperature(engineTemperature);
        racerData.setOilPressure(oilPressure);
        racerData.setSpeed(speed);
        racerData.setTirePressure(tirePressure);
        racerData.setTime(new Date());
        observers.forEach(observer -> observer.update(racerData));
    }
}
