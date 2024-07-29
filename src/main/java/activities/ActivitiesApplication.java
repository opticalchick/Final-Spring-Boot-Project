package activities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("activities")
@SpringBootApplication
public class ActivitiesApplication {

	public static void main(String[] args) {
		//start Spring Boot
		SpringApplication.run(ActivitiesApplication.class, args);
	}

}


