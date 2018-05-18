package pl.devone.sri.jmsapp.racer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class RacerData {
    private int racerId;
    private double engineTemperature;
    private double[] tirePressure;
    private double oilPressure;
    private double speed;
    private Date time;
}
