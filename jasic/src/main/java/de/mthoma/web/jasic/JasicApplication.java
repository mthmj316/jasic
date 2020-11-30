package de.mthoma.web.jasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class JasicApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(JasicApplication.class, args);
	}

}
