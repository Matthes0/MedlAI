package pl.umcs.medlai.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AppointmentDTO {
    private Integer id;
    private Integer doctorID;
    private LocalTime startTime;
    private LocalDate date;
}
