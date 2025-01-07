package pl.umcs.medlai.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentConfirmationDTO {
    private String doctor_first_name;
    private String doctor_last_name;
    private LocalDateTime start_date;
    private String patient_first_name;
    private String patient_last_name;
    private String patient_email;
    private String patient_phone;
    private String patient_pesel;
    private String patient_address;
}
