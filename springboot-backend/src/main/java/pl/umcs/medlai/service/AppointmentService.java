package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AdminAppointmentDTO;
import pl.umcs.medlai.dto.AppointmentBookedDTO;
import pl.umcs.medlai.dto.AppointmentDTO;
import pl.umcs.medlai.model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private DoctorDAO doctorDAO;

    public AppointmentService(AppointmentDAO appointmentDAO, DoctorDAO doctorDAO) {
        this.appointmentDAO = appointmentDAO;
        this.doctorDAO = doctorDAO;
    }

    public Optional<Appointment> getById(Integer id) {
        return this.appointmentDAO.getById(id);
    }


    @Transactional
    public void saveOrUpdate(Appointment appointment) {
        this.appointmentDAO.saveOrUpdate(appointment);
    }

    @Transactional
    public void delete(Integer id) {
        appointmentDAO.delete(id);
    }

    public List<AppointmentDTO> generateAvailableAppointments(Integer doctorID, String date) {
        List<AppointmentDTO> appointments = new ArrayList<>();
        Optional<Doctor> doctorOptional = doctorDAO.getById(doctorID);
        DateTimeFormatter formatter;
        if (date.length() == 9) {
            formatter = DateTimeFormatter.ofPattern("yyyy-dd-M");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        }
        LocalDate receivedDate = LocalDate.parse(date, formatter);
        System.out.println(receivedDate);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            List<Schedule> scheduleList = doctor.getSchedule();
            List<Absence> absenceList = doctor.getAbsence();
            for (Absence absence : absenceList) {
                if (!(receivedDate.isBefore(absence.getStart_date()) || receivedDate.isAfter(absence.getEnd_date()))) {
                    return appointments;
                }
            }
            for (Schedule schedule : scheduleList) {
                if ((schedule.getDay_of_week().toString()).equals(receivedDate.getDayOfWeek().toString()) && !(receivedDate.isAfter(schedule.getValid_to()))) {
                    appointments.addAll(generateAppointmentsForSchedule(doctor, schedule, receivedDate));
                }
            }
        }
        return appointments;
    }

    public List<AppointmentDTO> generateAppointmentsForSchedule(Doctor doctor, Schedule schedule, LocalDate date) {

        List<AppointmentDTO> appointments = new ArrayList<>();
        int id = 1;
        LocalTime startTime = schedule.getStart_time();
        LocalTime endTime = schedule.getEnd_time();
        while (!(startTime.plusMinutes(30)).isAfter(endTime)) {
            Optional<Appointment> existingAppointment = this.appointmentDAO.getByDateAndId(date.atTime(startTime), doctor.getId());
            if (existingAppointment.isEmpty() || existingAppointment.get().getStatus() == Status.CANCELLED) {
                appointments.add(new AppointmentDTO(
                        id,
                        doctor.getId(),
                        startTime,
                        date
                ));
                id += 1;
            }
            startTime = startTime.plusMinutes(30);
        }

        return appointments;
    }

    public Appointment createAppointmentFromBookedDTO(AppointmentBookedDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        Optional<Doctor> doctor = this.doctorDAO.getById(appointmentDTO.getDoctor_id());
        doctor.ifPresent(appointment::setDoctor);
        appointment.setStart_date(appointmentDTO.getStart_date());
        appointment.setPatient_first_name(appointmentDTO.getPatient_first_name());
        appointment.setPatient_last_name(appointmentDTO.getPatient_last_name());
        appointment.setPatient_email(appointmentDTO.getPatient_email());
        appointment.setPatient_phone(appointmentDTO.getPatient_phone());
        appointment.setPatient_address(appointmentDTO.getPatient_address());
        appointment.setPatient_pesel(appointmentDTO.getPatient_pesel());
        appointment.setStatus(appointmentDTO.getStatus());
        return appointment;
    }

    public List<AdminAppointmentDTO> getAllAppointments() {
        List<AdminAppointmentDTO> appointmentDTOS = new ArrayList<>();
        List<Appointment> appointments = appointmentDAO.getAll();
        for (Appointment appointment : appointments) {
            appointmentDTOS.add(new AdminAppointmentDTO(
                    appointment.getId(),
                    appointment.getStart_date(),
                    appointment.getPatient_first_name(),
                    appointment.getPatient_last_name(),
                    appointment.getPatient_email(),
                    appointment.getPatient_phone(),
                    appointment.getPatient_address(),
                    appointment.getPatient_pesel(),
                    appointment.getDoctor().getId(),
                    appointment.getStatus()
            ));
        }
        return appointmentDTOS;
    }

    public Optional<Appointment> getAppointmentById(Integer appointmentId) {
        return appointmentDAO.getById(appointmentId);
    }

    @Transactional
    public Appointment createAppointment(Appointment appointmentDetails) {
        return appointmentDAO.save(appointmentDetails);
    }

    @Transactional
    public Appointment updateAppointment(Integer appointmentId, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentDAO.getById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStart_date(appointmentDetails.getStart_date());
            appointment.setPatient_first_name(appointmentDetails.getPatient_first_name());
            appointment.setPatient_last_name(appointmentDetails.getPatient_last_name());
            appointment.setPatient_email(appointmentDetails.getPatient_email());
            appointment.setPatient_phone(appointmentDetails.getPatient_phone());
            appointment.setPatient_address(appointmentDetails.getPatient_address());
            appointment.setPatient_pesel(appointmentDetails.getPatient_pesel());
            appointment.setStatus(appointmentDetails.getStatus());
            return appointmentDAO.save(appointment);
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
        }
    }

    @Transactional
    public void deleteAppointment(Integer appointmentId) {
        if (!appointmentDAO.existsById(appointmentId)) {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
        }
        appointmentDAO.delete(appointmentId);
    }
}
