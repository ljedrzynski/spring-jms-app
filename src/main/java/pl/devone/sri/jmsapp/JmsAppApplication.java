package pl.devone.sri.jmsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.devone.sri.jmsapp.racer.Racer;

import java.util.concurrent.TimeUnit;


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

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            racer.setEngineTemperature(randomWithRange(70, 130));
            racer.setOilPressure(randomWithRange(60, 150));
            racer.setTirePressure(new double[]{randomWithRange(1.1, 1.6), randomWithRange(1.1, 1.6), randomWithRange(1.1, 1.5), randomWithRange(1.1, 1.5)});
            racer.setSpeed(randomWithRange(0, 350));
        };
        task.run();
    }

    private static double randomWithRange(double min, double max) {
        double range = (max - min);
        return (Math.random() * range) + min;
    }
}
