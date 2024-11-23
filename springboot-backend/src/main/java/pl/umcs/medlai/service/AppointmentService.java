package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.model.Appointment;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Transactional
    public Optional<Appointment> getById(Integer id) {
        return this.appointmentDAO.getById(id);
    }
    @Transactional
    public List<Appointment> getAll() {
        return this.appointmentDAO.getAll();
    }
    @Transactional
    public void saveOrUpdate(Appointment appointment) {
        this.appointmentDAO.saveOrUpdate(appointment);
    }
    @Transactional
    public void delete(Integer id){
        appointmentDAO.delete(id);
    }
}
