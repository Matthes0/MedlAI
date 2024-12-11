package pl.umcs.medlai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.Specialization;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private Integer id;
    private String name;
    private Specialization title;
}
