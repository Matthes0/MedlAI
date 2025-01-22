package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.AbsenceDAO;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AbsenceDTO;
import pl.umcs.medlai.model.Absence;
import pl.umcs.medlai.model.Doctor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {

    private final DoctorDAO doctorDAO;

    private final AbsenceDAO absenceDAO;

    public AbsenceService(DoctorDAO doctorDAO, AbsenceDAO absenceDAO) {
        this.doctorDAO = doctorDAO;
        this.absenceDAO = absenceDAO;
    }

    @Transactional
    public void deleteAbsence(Integer absenceId) {
        if (!absenceDAO.existsById(absenceId)) {
            throw new IllegalArgumentException("Absence with ID " + absenceId + " not found.");
        }
        absenceDAO.delete(absenceId);
    }
    public List<AbsenceDTO> getAllAbsences() {
        List<AbsenceDTO> absenceDTOs = new ArrayList<>();
        List<Absence> absences = absenceDAO.findAll();
        for (Absence absence : absences) {
            absenceDTOs.add(new AbsenceDTO(
                    absence.getDoctor().getId(),
                    absence.getId(),
                    absence.getStart_date(),
                    absence.getEnd_date(),
                    absence.getAbsence_reason()
            ));
        }
        return absenceDTOs;
    }
    public AbsenceDTO getAbsenceById(Integer absenceId) {
        Optional<Absence> optionalAbsence = absenceDAO.getById(absenceId);
        if (optionalAbsence.isPresent()) {
            Absence absence = optionalAbsence.get();
            return new AbsenceDTO(
                    absence.getDoctor().getId(),
                    absence.getId(),
                    absence.getStart_date(),
                    absence.getEnd_date(),
                    absence.getAbsence_reason());
        } else {
            throw new IllegalArgumentException("Absence with ID " + absenceId + " not found.");
        }
    }

    @Transactional
    public Absence createAbsence(AbsenceDTO absenceDetails) {
        Optional<Doctor> optionalDoctor = doctorDAO.getById(absenceDetails.getDoctor_id());
        System.out.println(optionalDoctor.isPresent());
        if (optionalDoctor.isEmpty()) {
            throw new IllegalArgumentException("Doctor with ID " + absenceDetails.getDoctor_id()+ " not found.");
        }
        Doctor doctor = optionalDoctor.get();
        Absence absence = new Absence();
        absence.setDoctor(doctor);
        absence.setStart_date(absenceDetails.getStart_date());
        absence.setEnd_date(absenceDetails.getEnd_date());
        absence.setAbsence_reason(absenceDetails.getAbsence_reason());

        return absenceDAO.save(absence);
    }
    @Transactional
    public Absence updateAbsence(Integer absenceId, Absence absenceDetails) {
        Optional<Absence> optionalAbsence = absenceDAO.getById(absenceId);

        if (optionalAbsence.isPresent()) {
            Absence existingAbsence = optionalAbsence.get();

            existingAbsence.setStart_date(absenceDetails.getStart_date());
            existingAbsence.setEnd_date(absenceDetails.getEnd_date());
            existingAbsence.setAbsence_reason(absenceDetails.getAbsence_reason());

            if (absenceDetails.getDoctor() != null) {
                Optional<Doctor> optionalDoctor = doctorDAO.getById(absenceDetails.getDoctor().getId());
                if (optionalDoctor.isPresent()) {
                    existingAbsence.setDoctor(optionalDoctor.get());
                } else {
                    throw new IllegalArgumentException("Doctor with ID " + absenceDetails.getDoctor().getId() + " not found.");
                }
            }

            return absenceDAO.save(existingAbsence);
        } else {
            throw new IllegalArgumentException("Absence with ID " + absenceId + " not found.");
        }
    }
}