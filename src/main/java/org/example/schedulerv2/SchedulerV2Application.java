package org.example.schedulerv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@PropertySource("classpath:secrets.properties")
@SpringBootApplication
@EnableJpaAuditing
public class SchedulerV2Application {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerV2Application.class, args);
    }

}
