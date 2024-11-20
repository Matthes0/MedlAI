package pl.umcs.medlai.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.umcs.medlai.model.Appointment;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private final String GET_ALL_JPQL = "FROM pl.umcs.medlai.model.Appointment";
    private final String GET_BY_ID_JPQL= "SELECT b FROM pl.umcs.medlai.model.Appointment b WHERE b.id = :id";
    public AppointmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public Optional<Appointment> getById(long id){
        TypedQuery<Appointment> query = entityManager.createQuery(GET_BY_ID_JPQL,Appointment.class);
        query.setParameter("id", id);
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
    public void saveOrUpdate(Appointment appointment){
        System.out.println("APPOINTMENT "+ appointment);
        if ( getById(appointment.getId()).isEmpty() ) {
            entityManager.persist(appointment);
        } else {
            entityManager.merge(appointment);
        }
    }
    public void delete(long id){
        getById(id).ifPresent(appointment -> entityManager.remove(appointment));
    }
}
