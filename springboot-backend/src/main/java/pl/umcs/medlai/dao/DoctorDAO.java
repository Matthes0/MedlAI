package pl.umcs.medlai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.model.Doctor;

import java.util.List;

@Repository
public class DoctorDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private final String GET_ALL_JPQL = "FROM pl.umcs.medlai.model.Doctor";
    public DoctorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Doctor> getAll(){
        TypedQuery<Doctor> query = entityManager.createQuery(GET_ALL_JPQL, Doctor.class);
        return query.getResultList();
    }
}
