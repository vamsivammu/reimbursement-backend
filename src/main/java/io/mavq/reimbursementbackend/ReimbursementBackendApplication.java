package io.mavq.reimbursementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class ReimbursementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReimbursementBackendApplication.class, args);
	}

}
