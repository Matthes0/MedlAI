package pl.umcs.medlai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Schedule;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private final String GET_BY_ID_JPQL = "SELECT s FROM pl.umcs.medlai.model.Schedule s WHERE s.id = :id";
    private final String FIND_ALL_BY_DOCTOR_ID = "SELECT s FROM pl.umcs.medlai.model.Schedule s WHERE s.doctor.id = :doctor_id";
    private final String FIND_ALL_JPQL = "SELECT s FROM pl.umcs.medlai.model.Schedule s";

    public ScheduleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Schedule> getById(Integer id) {
        TypedQuery<Schedule> query = entityManager.createQuery(GET_BY_ID_JPQL, Schedule.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Schedule> findAllByDoctorId(Integer doctorId) {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_ALL_BY_DOCTOR_ID, Schedule.class);
        query.setParameter("doctor_id", doctorId);
        return query.getResultList();
    }

    public Schedule save(Schedule schedule) {
        if (getById(schedule.getId()).isEmpty()) {
            entityManager.persist(schedule);
            return schedule;
        } else {
            return entityManager.merge(schedule);
        }
    }

    public void delete(Integer id) {
        getById(id).ifPresent(schedule -> entityManager.remove(schedule));
    }

    public boolean existsById(Integer id) {
        return getById(id).isPresent();
    }

    public List<Schedule> findAll() {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_ALL_JPQL, Schedule.class);
        return query.getResultList();
    }
}