package pl.umcs.medlai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.model.Status;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private final String GET_ALL_JPQL = "FROM pl.umcs.medlai.model.Appointment";
    private final String GET_BY_ID_JPQL= "SELECT b FROM pl.umcs.medlai.model.Appointment b WHERE b.id = :id";
    private final String GET_BY_DATE_JPQL= "SELECT b FROM pl.umcs.medlai.model.Appointment b WHERE b.start_date = :start_date AND b.status != 'CANCELLED'";
    private final String GET_BY_DOCTOR_ID_JPQL = "SELECT b FROM pl.umcs.medlai.model.Appointment b WHERE b.doctor.id = :doctorId";
    private final String GET_BY_STATUS_JPQL = "SELECT b FROM pl.umcs.medlai.model.Appointment b WHERE b.status = :status";
    public AppointmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public Optional<Appointment> getById(Integer id){
        TypedQuery<Appointment> query = entityManager.createQuery(GET_BY_ID_JPQL,Appointment.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Optional<Appointment> getByDate(LocalDateTime date){
        TypedQuery<Appointment> query = entityManager.createQuery(GET_BY_DATE_JPQL,Appointment.class);
        query.setParameter("start_date", date);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public List<Appointment> getAll(){
        TypedQuery<Appointment> query = entityManager.createQuery(GET_ALL_JPQL, Appointment.class);
        return query.getResultList();
    }

    public Appointment saveOrUpdate(Appointment appointment){
        System.out.println("APPOINTMENT "+ appointment);
        if ( getById(appointment.getId()).isEmpty() ) {
            entityManager.persist(appointment);
            return appointment;
        } else {
            return entityManager.merge(appointment);
        }
    }
    public void delete(Integer id){
        getById(id).ifPresent(appointment -> entityManager.remove(appointment));
    }

    public List<Appointment> findByDoctorId(Integer doctorId) {
        TypedQuery<Appointment> query = entityManager.createQuery(GET_BY_DOCTOR_ID_JPQL, Appointment.class);
        query.setParameter("doctorId", doctorId);
        return query.getResultList();
    }
    public List<Appointment> findByStatus(Status status) {
        TypedQuery<Appointment> query = entityManager.createQuery(GET_BY_STATUS_JPQL, Appointment.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    @Transactional
    public Appointment save(Appointment appointment){
        System.out.println("APPOINTMENT "+ appointment);
        if ( getById(appointment.getId()).isEmpty() ) {
            entityManager.persist(appointment);
            return appointment;
        } else {
            return entityManager.merge(appointment);
        }
    }
    public boolean existsById(Integer id){
        return getById(id).isPresent();
    }
}
