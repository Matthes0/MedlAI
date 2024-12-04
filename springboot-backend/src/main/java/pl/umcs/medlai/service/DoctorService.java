package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.model.Status;
import pl.umcs.medlai.repository.DoctorRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class DoctorService {
    private DoctorRepository doctorRepository;

    @Transactional
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public void updateDoctor(Long id, Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(Math.toIntExact(id));
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setFirst_name(doctorDetails.getFirst_name());
            doctor.setLast_name(doctorDetails.getLast_name());
            doctor.setEmail(doctorDetails.getEmail());
            doctor.setPhone(doctorDetails.getPhone());
            doctor.setSpecialization(doctorDetails.getSpecialization());
            doctor.setSchedule(doctorDetails.getSchedule());
            doctor.setAbsence(doctorDetails.getAbsence());
            doctorRepository.save(doctor);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + id + " not found.");
        }

    }
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Doctor with ID " + id + " not found.");
        }
        doctorRepository.deleteById(Math.toIntExact(id));
    }
    public boolean hasFutureAppointments(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(Math.toIntExact(doctorId));

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            return doctor.getAppointments().stream()
                    .anyMatch(appointment ->
                            appointment.getStart_date().isAfter(LocalDateTime.now()) &&
                                    appointment.getStatus() == Status.SCHEDULED);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }
    }
}
