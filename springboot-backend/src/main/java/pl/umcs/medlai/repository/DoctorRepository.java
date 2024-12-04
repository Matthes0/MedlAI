package pl.umcs.medlai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    boolean existsById(Integer id);
}
