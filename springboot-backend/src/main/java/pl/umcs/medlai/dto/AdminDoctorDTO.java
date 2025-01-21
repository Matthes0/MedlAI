package pl.umcs.medlai.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Specialization;

@Data
@AllArgsConstructor
public class AdminDoctorDTO {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

}
