package pl.umcs.medlai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Absence;

import java.util.List;
import java.util.Optional;

@Repository
public class AbsenceDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private final String GET_BY_ID_JPQL = "SELECT a FROM pl.umcs.medlai.model.Absence a WHERE a.id = :id";
    private final String FIND_ALL_BY_DOCTOR_ID = "SELECT a FROM pl.umcs.medlai.model.Absence a WHERE a.doctor.id = :doctor_id";
    private final String FIND_ALL_JPQL = "SELECT a FROM pl.umcs.medlai.model.Absence a";

    public AbsenceDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Absence> getById(Integer id) {
        TypedQuery<Absence> query = entityManager.createQuery(GET_BY_ID_JPQL, Absence.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Absence> findAllByDoctorId(Integer doctor_id) {
        TypedQuery<Absence> query = entityManager.createQuery(FIND_ALL_BY_DOCTOR_ID, Absence.class);
        query.setParameter("doctor_id", doctor_id);
        return query.getResultList();
    }

    public Absence save(Absence absence) {
        if (getById(absence.getId()).isEmpty()) {
            entityManager.persist(absence);
            return absence;
        } else {
            return entityManager.merge(absence);
        }
    }
    public void delete(Integer id) {
        getById(id).ifPresent(absence -> entityManager.remove(absence));
    }
    public boolean existsById(Integer id) {
        return getById(id).isPresent();
    }
    public List<Absence> findAll() {
        TypedQuery<Absence> query = entityManager.createQuery(FIND_ALL_JPQL, Absence.class);
        return query.getResultList();
    }

}
