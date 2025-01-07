package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Absence")
public class Absence {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private LocalDate start_date;
    @Setter
    @Getter
    private LocalDate end_date;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private AbsenceReason absence_reason;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @Setter
    @Getter
    private Doctor doctor;
}
