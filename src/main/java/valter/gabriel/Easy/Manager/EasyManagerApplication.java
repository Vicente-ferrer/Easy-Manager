package valter.gabriel.Easy.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import valter.gabriel.Easy.Manager.service.EmailService;

@SpringBootApplication
@EnableScheduling
public class EasyManagerApplication {



	public static void main(String[] args) {
		SpringApplication.run(EasyManagerApplication.class, args);
	}



}
