package pl.umcs.medlai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.model.Status;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
     List<Appointment> findByDoctorId(Integer doctorId);
     List<Appointment> findByStatus(Status status);
}