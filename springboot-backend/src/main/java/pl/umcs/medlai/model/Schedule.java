package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="Schedule")
public class Schedule {
    @Id
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private DayOfWeek day_of_week;
    @Setter
    @Getter
    private LocalTime start_time;
    @Setter
    @Getter
    private LocalTime end_time;
    @Setter
    @Getter
    private LocalDateTime valid_to;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    @Setter
    @Getter
    private Doctor doctor;

}
