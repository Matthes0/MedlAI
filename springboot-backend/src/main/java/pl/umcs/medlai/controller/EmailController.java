package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.medlai.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

//    @GetMapping("/send-email")
//    public void sendConfirmationEmail(String to, String link) {
//        emailService.sendEmail(to,"Potwierdź wizytę w przychodni MedlAI", "Potwierdź mail pls <br> to link: " + link + "<br> Miłej wizyty, MedlAI team");
//    }

}
