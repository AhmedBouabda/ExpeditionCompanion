package tn.esprit.expeditioncompanion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan
@Configuration

public class ExpeditionCompanionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpeditionCompanionApplication.class, args);
	}

}
