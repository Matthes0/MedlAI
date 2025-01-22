package pl.umcs.medlai.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.DayOfWeek;
import pl.umcs.medlai.model.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleDTO {
    private int doctor_id;
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;
    private LocalTime start_time;
    private LocalTime end_time;
    private LocalDate valid_to;
//    @ManyToOne
//    @JoinColumn(name="doctor_id")

}
