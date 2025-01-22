package pl.umcs.medlai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime start_date;
    private String patient_first_name;
    private String patient_last_name;
    private String patient_email;
    private String patient_phone;
    private String patient_address;
    private String patient_pesel;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @JsonBackReference
    @ToString.Exclude
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private Status status;
}
