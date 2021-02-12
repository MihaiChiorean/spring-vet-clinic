package ro.fasttrackit.vetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringVetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringVetClinicApplication.class, args);
	}

}
