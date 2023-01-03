package valter.gabriel.Easy.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import valter.gabriel.Easy.Manager.service.EmailService;

@SpringBootApplication
public class EasyManagerApplication {



	public static void main(String[] args) {
		SpringApplication.run(EasyManagerApplication.class, args);
	}


}
