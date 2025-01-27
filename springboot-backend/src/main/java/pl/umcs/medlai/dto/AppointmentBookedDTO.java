package pl.umcs.medlai.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentBookedDTO {
    private Integer doctor_id;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
            message = "Date must follow the format yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start_date;
    private String patient_first_name;
    private String patient_last_name;
    private String patient_email;
    private String patient_phone;
    private String patient_pesel;
    private String patient_address;
    private Status status;
}
