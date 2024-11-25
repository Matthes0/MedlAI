package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;
    @Setter
    @Getter
    private LocalTime start_time;
    @Setter
    @Getter
    private LocalTime end_time;
    @Setter
    @Getter
    private LocalDate valid_to;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @Setter
    @Getter
    private Doctor doctor;

}
