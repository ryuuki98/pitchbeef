package com.pitchbeef.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PitchbeefApplication {

    public static void main(String[] args) {
        SpringApplication.run(PitchbeefApplication.class, args);
    }

}
