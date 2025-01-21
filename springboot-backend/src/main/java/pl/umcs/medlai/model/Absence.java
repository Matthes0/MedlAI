package pl.umcs.medlai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="Absence")
public class Absence {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private LocalDate start_date;
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private AbsenceReason absence_reason;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @JsonBackReference
    private Doctor doctor;
}
