package pl.umcs.medlai.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminAppointmentDTO {

    private Integer id;
    private LocalDateTime start_date;
    private String patient_first_name;
    private String patient_last_name;
    private String patient_email;
    private String patient_phone;
    private String patient_address;
    private String patient_pesel;
    private int doctor_id;
    @Enumerated(EnumType.STRING)
    private Status status;
}
