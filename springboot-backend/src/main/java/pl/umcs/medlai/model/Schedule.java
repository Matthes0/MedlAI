package pl.umcs.medlai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name="Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;
    private LocalTime start_time;
    private LocalTime end_time;
    private LocalDate valid_to;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @JsonBackReference
    private Doctor doctor;

}
