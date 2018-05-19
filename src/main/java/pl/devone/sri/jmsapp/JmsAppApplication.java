package pl.devone.sri.jmsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.devone.sri.jmsapp.racer.Racer;

@SpringBootApplication
@EnableScheduling
@EnableJms
public class JmsAppApplication {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(JmsAppApplication.class, args);
        go();
    }

    private static void go() {
        Racer racer = applicationContext.getBean(Racer.class);
        racer.setRacerId(1);
        while (true) {
            try {
                Thread.sleep(3000);
                racer.setEngineTemperature(randomWithRange(60, 100));
                racer.setOilPressure(randomWithRange(50, 150));
                racer.setTirePressure(new double[]{randomWithRange(1, 2), randomWithRange(1, 2), randomWithRange(1, 1.5), randomWithRange(1, 1.5)});
                racer.setSpeed(randomWithRange(0, 350));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static double randomWithRange(double min, double max) {
        double range = (max - min);
        return (Math.random() * range) + min;
    }
}
