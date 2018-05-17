package pl.devone.sri.jmsapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
public class RacerData {
    private int racerId;
    private double engineTemperature;
    private double[] tirePressure;
    private double oilPressure;
    private double speed;
    private LocalDateTime time;
}
