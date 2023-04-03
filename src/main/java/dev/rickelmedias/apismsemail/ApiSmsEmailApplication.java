package dev.rickelmedias.apismsemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ApiSmsEmailApplication {

	private final Environment env;

	public ApiSmsEmailApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiSmsEmailApplication.class);
		Environment env = app.run(args).getEnvironment();

		// Insert sample: insert into users values (null, 'Rick', 'email_recieve_mail@gmail.com', '123.456.789-10', '(55) 99710-3434')
	}
}
