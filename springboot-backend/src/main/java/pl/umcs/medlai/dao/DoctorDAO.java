package pl.umcs.medlai.dao;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public class DoctorDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private final String GET_ALL_JPQL = "FROM pl.umcs.medlai.model.Doctor";
    private final String GET_BY_ID_JPQL = "SELECT b FROM pl.umcs.medlai.model.Doctor b WHERE b.id = :id";

    public DoctorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Doctor> getAll(){
        TypedQuery<Doctor> query = entityManager.createQuery(GET_ALL_JPQL, Doctor.class);
        return query.getResultList();
    }
    public Optional<Doctor> getById(Integer id){
        TypedQuery<Doctor> query = entityManager.createQuery(GET_BY_ID_JPQL,Doctor.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Transactional
    public Doctor save(Doctor doctor){
        if (getById(doctor.getId()).isEmpty()) {
            entityManager.persist(doctor);
            return doctor;
        } else {
            return entityManager.merge(doctor);
        }
    }
@Transactional
    public void delete(Integer id){
        getById(id).ifPresent(doctor -> entityManager.remove(doctor));
    }
    public boolean existsById(Integer id){
        return getById(id).isPresent();
    }
}
