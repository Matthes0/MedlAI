package pl.umcs.medlai.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dao.ScheduleDAO;
import pl.umcs.medlai.dto.ScheduleDTO;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.model.Schedule;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final DoctorDAO doctorDAO;

    private final ScheduleDAO scheduleDAO;

    public ScheduleService(DoctorDAO doctorDAO, ScheduleDAO scheduleDAO) {
        this.doctorDAO = doctorDAO;
        this.scheduleDAO = scheduleDAO;
    }

    @Transactional
    public void deleteSchedule(Integer scheduleId) {
        if (!scheduleDAO.existsById(scheduleId)) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleId + " not found.");
        }
        scheduleDAO.delete(scheduleId);
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> schedulesDTOs = new ArrayList<>();
        List<Schedule> schedules = scheduleDAO.findAll();
        for (Schedule schedule : schedules) {
            schedulesDTOs.add(new ScheduleDTO(
                    schedule.getDoctor().getId(),
                    schedule.getId(),
                    schedule.getDay_of_week(),
                    schedule.getStart_time(),
                    schedule.getEnd_time(),
                    schedule.getValid_to()
            ));
        }
        return schedulesDTOs;
    }

    public ScheduleDTO getScheduleById(Integer scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleDAO.getById(scheduleId);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            return new ScheduleDTO(
                    schedule.getDoctor().getId(),
                    schedule.getId(),
                    schedule.getDay_of_week(),
                    schedule.getStart_time(),
                    schedule.getEnd_time(),
                    schedule.getValid_to());
        } else {
            throw new IllegalArgumentException("Schedule with ID " + scheduleId + " not found.");
        }
    }

    @Transactional
    public Schedule createSchedule(ScheduleDTO scheduleDetails) {
        Optional<Doctor> optionalDoctor = doctorDAO.getById(scheduleDetails.getDoctor_id());
        if (optionalDoctor.isEmpty()) {
            throw new IllegalArgumentException("Doctor with ID " + scheduleDetails.getDoctor_id() + " not found.");
        }
        Doctor doctor = optionalDoctor.get();
        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setDay_of_week(scheduleDetails.getDay_of_week());
        schedule.setStart_time(scheduleDetails.getStart_time());
        schedule.setEnd_time(scheduleDetails.getEnd_time());
        schedule.setValid_to(scheduleDetails.getValid_to());

        return scheduleDAO.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Integer scheduleId, Schedule scheduleDetails) {
        Optional<Schedule> optionalSchedule = scheduleDAO.getById(scheduleId);

        if (optionalSchedule.isPresent()) {
            Schedule existingSchedule = optionalSchedule.get();

            existingSchedule.setDay_of_week(scheduleDetails.getDay_of_week());
            existingSchedule.setStart_time(scheduleDetails.getStart_time());
            existingSchedule.setEnd_time(scheduleDetails.getEnd_time());
            existingSchedule.setValid_to(scheduleDetails.getValid_to());

            if (scheduleDetails.getDoctor() != null) {
                Optional<Doctor> optionalDoctor = doctorDAO.getById(scheduleDetails.getDoctor().getId());
                if (optionalDoctor.isPresent()) {
                    existingSchedule.setDoctor(optionalDoctor.get());
                } else {
                    throw new IllegalArgumentException("Doctor with ID " + scheduleDetails.getDoctor().getId() + " not found.");
                }
            }
            return scheduleDAO.save(existingSchedule);
        } else {
            throw new IllegalArgumentException("Schedule with ID " + scheduleId + " not found.");
        }
    }
}