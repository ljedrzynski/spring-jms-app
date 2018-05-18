package pl.devone.sri.jmsapp.racer.jms;

import pl.devone.sri.jmsapp.racer.dto.RacerData;

public interface IRacerObserver {
    void update(RacerData racer);
}
