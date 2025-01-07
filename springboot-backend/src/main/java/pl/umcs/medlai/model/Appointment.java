package pl.umcs.medlai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private LocalDateTime start_date;
//    @Setter
//    @Getter
//    private LocalDateTime end_date;
    @Setter
    @Getter
    private String patient_first_name;
    @Setter
    @Getter
    private String patient_last_name;
    @Setter
    @Getter
    private String patient_email;
    @Setter
    @Getter
    private String patient_phone;
    @Setter
    @Getter
    private String patient_address;
    @Setter
    @Getter
    private String patient_pesel;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @Setter
    @Getter
    private Doctor doctor;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Status status;
}
