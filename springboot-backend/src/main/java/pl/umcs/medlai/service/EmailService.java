package pl.umcs.medlai.service;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.configure.JWTUtil;
import pl.umcs.medlai.model.Appointment;

import java.util.Map;

@Service
public class EmailService {

    private static final long EXPIRATION_TIME = 900000; // 15 minutes in milliseconds

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JWTUtil jwt;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("noreply@medlai.com");
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public String generateAppointmentLink(Appointment appointment) {
        Map<String, Object> claims = Map.of(
                "appointmentId", appointment.getId(),
                "patientEmail", appointment.getPatient_email()
        );
        String token = jwt.generateToken(claims, EXPIRATION_TIME);
        return "http://localhost:5173/appointment/confirm?token=" + token;
    }

    public Integer validateAppointmentLink(String token) {
        Claims claims = jwt.validateToken(token);
        return (Integer) claims.get("appointmentId");
    }

}
