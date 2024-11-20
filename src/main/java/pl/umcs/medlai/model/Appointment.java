package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="Appointment")
public class Appointment {
    @Id
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private LocalDateTime start_date;
    @Setter
    @Getter
    private LocalDateTime end_date = start_date.plusMinutes(30);
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
