package pl.umcs.medlai.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.umcs.medlai.model.AbsenceReason;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AbsenceDTO {
    private int doctor_id;
    private int id;
    private LocalDate start_date;
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private AbsenceReason absence_reason;
}
