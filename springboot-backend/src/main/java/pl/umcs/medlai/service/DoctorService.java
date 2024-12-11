package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AppointmentDTO;
import pl.umcs.medlai.dto.DoctorDTO;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.model.Schedule;
import pl.umcs.medlai.model.Status;
import pl.umcs.medlai.repository.DoctorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorDAO doctorDAO;

    @Transactional
    public List<DoctorDTO> getAll() {
        List<DoctorDTO> doctors_to_send = new ArrayList<>();
        List<Doctor> doctors = doctorDAO.getAll();
        for (Doctor doctor : doctors) {
            doctors_to_send.add(new DoctorDTO(doctor.getId(), "Dr. "+doctor.getFirst_name()+" "+doctor.getLast_name(),doctor.getSpecialization()));
        }
        return doctors_to_send;
    }

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
