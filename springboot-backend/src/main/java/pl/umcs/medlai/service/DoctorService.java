package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AdminDoctorDTO;
import pl.umcs.medlai.dto.DoctorDTO;
import pl.umcs.medlai.model.Doctor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    public DoctorService(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    private final DoctorDAO doctorDAO;

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
    public Doctor updateDoctor(Integer doctorId, Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorDAO.getById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor existingDoctor = optionalDoctor.get();

            existingDoctor.setFirst_name(doctorDetails.getFirst_name());
            existingDoctor.setLast_name(doctorDetails.getLast_name());
            existingDoctor.setEmail(doctorDetails.getEmail());
            existingDoctor.setPhone(doctorDetails.getPhone());
            existingDoctor.setSpecialization(doctorDetails.getSpecialization());
            return doctorDAO.save(existingDoctor);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }
    }
    public void deleteDoctor(Integer id) {
        if (!doctorDAO.existsById(id)) {
            throw new IllegalArgumentException("Doctor with ID " + id + " not found.");
        }
        doctorDAO.delete(id);
    }

    @Transactional
    public AdminDoctorDTO getDoctorById(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorDAO.getById(Math.toIntExact(doctorId));
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            return new AdminDoctorDTO(
                    doctor.getId(),
                    doctor.getFirst_name(),
                    doctor.getLast_name(),
                    doctor.getEmail(),
                    doctor.getPhone(),
                    doctor.getSpecialization());
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }
    }

    @Transactional
    public Doctor createDoctor(Doctor doctorDetails) {
        return doctorDAO.save(doctorDetails);
    }
    public List<AdminDoctorDTO> getDoctorsAdmin(){
        List<AdminDoctorDTO> doctors_to_send = new ArrayList<>();
        List<Doctor> doctors = doctorDAO.getAll();
        for (Doctor doctor : doctors) {
            doctors_to_send.add(new AdminDoctorDTO(
                    doctor.getId(),
                    doctor.getFirst_name(),
                    doctor.getLast_name(),
                    doctor.getEmail(),
                    doctor.getPhone(),
                    doctor.getSpecialization()));
        }
        return doctors_to_send;
    }
}
