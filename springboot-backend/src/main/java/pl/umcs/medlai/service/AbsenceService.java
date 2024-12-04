package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.model.Absence;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.repository.AbsenceRepository;
import pl.umcs.medlai.repository.DoctorRepository;

import java.util.Optional;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public Absence setDoctorAbsence(Integer doctorId, Absence absenceDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            Absence absence = new Absence();
            absence.setDoctor(doctor);
            absence.setStart_date(absenceDetails.getStart_date());
            absence.setEnd_date(absenceDetails.getEnd_date());
            absence.setAbsence_reason(absenceDetails.getAbsence_reason());
            return absenceRepository.save(absence);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }
    }
}