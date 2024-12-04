package pl.umcs.medlai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Absence;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
}
