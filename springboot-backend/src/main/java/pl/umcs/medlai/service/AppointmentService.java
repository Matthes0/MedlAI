package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AppointmentDTO;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.model.Schedule;
import pl.umcs.medlai.model.Status;
import pl.umcs.medlai.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorDAO doctorDAO;
    public AppointmentService(AppointmentDAO appointmentDAO, DoctorDAO doctorDAO) {
        this.appointmentDAO = appointmentDAO;
        this.doctorDAO = doctorDAO;
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

    @Transactional
    public List<AppointmentDTO> generateAvailableAppointments(Integer doctorID, String date) {
        List<AppointmentDTO> appointments = new ArrayList<>();
        Optional<Doctor> doctorOptional = doctorDAO.getById(doctorID);
        DateTimeFormatter formatter;
        if (date.length() == 9){
            formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        } else{
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        }
        LocalDate receivedDate = LocalDate.parse(date, formatter);
        System.out.println(receivedDate);
        if (doctorOptional.isPresent()){
            Doctor doctor = doctorOptional.get();
            for (Schedule schedule : doctor.getSchedule()) {
                if ((schedule.getDay_of_week().toString()).equals(receivedDate.getDayOfWeek().toString())){
                    appointments.addAll(generateAppointmentsForSchedule(doctor, schedule, receivedDate));
                }
            }
        }

        //List<Doctor> doctors = doctorDAO.getAll();
//        for (Doctor doctor : doctors) {
//            for (Schedule schedule : doctor.getSchedule()) {
//                appointments.addAll(generateAppointmentsForSchedule(doctor, schedule));
//            }
//        }
        return appointments;
    }
    private List<AppointmentDTO> generateAppointmentsForSchedule(Doctor doctor, Schedule schedule, LocalDate date) {

        //LocalDate receivedDate = LocalDate.parse(dateRequest.substring(0,10));
        List<AppointmentDTO> appointments = new ArrayList<>();
//        LocalDate start_day = LocalDate.now();
//        LocalDate valid_to = schedule.getValid_to();
        Integer id = 1;
        LocalTime startTime = schedule.getStart_time();
        LocalTime endTime = schedule.getEnd_time();
        while (!(startTime.plusMinutes(30)).isAfter(endTime)) {
            appointments.add(new AppointmentDTO(
                    id,
                    doctor.getId(),
                    startTime,
                    date
            ));
            startTime = startTime.plusMinutes(30);
            id += 1;
        }


//        for (LocalDate date = receivedDate; !date.isAfter(valid_to); date = date.plusDays(7)) {
//                LocalTime startTime = schedule.getStart_time();
//                LocalTime endTime = schedule.getEnd_time();
//                while (!(startTime.plusMinutes(30)).isAfter(endTime)) {
//                    appointments.add(new AppointmentDTO(
//                            id,
//                            doctor.getId(),
//                            startTime,
//                            date
//                    ));
//                    startTime = startTime.plusMinutes(30);
//                    id += 1;
//                }
//        }
        return appointments;
    }

    @Transactional
    public Appointment updateAppointmentStatus(Integer appointmentId, Status newStatus) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(newStatus);
            return appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
        }
    }

}
