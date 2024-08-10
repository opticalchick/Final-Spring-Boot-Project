package activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("activity")
@SpringBootApplication
public class ActivityApplication {

	public static void main(String[] args) {
		//start Spring Boot
		SpringApplication.run(ActivityApplication.class, args);
	}

}


