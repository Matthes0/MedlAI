package pl.umcs.medlai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.umcs.medlai.service.EmailService;

@SpringBootApplication
public class MedlAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedlAiApplication.class, args);
		EmailService emailService = new EmailService();
		emailService.sendEmail("test@gmail.com","testy w mainie", "umcs development");

	}

}
