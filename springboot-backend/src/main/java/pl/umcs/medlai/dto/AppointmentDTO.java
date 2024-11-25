package pl.umcs.medlai.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Specialization;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AppointmentDTO {
    private Integer doctorID;
    private String doctorFirstName;
    private String doctorLastName;
    private Specialization doctorSpecialization;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
}
