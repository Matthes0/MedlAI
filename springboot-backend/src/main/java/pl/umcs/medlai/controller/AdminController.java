package pl.umcs.medlai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.umcs.medlai.dto.*;
import pl.umcs.medlai.model.*;
import pl.umcs.medlai.service.AbsenceService;
import pl.umcs.medlai.service.AppointmentService;
import pl.umcs.medlai.service.DoctorService;
import pl.umcs.medlai.service.ScheduleService;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/admin")
public class AdminController {

    public AdminController(AppointmentService appointmentService, DoctorService doctorService, AbsenceService absenceService, ScheduleService scheduleService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.absenceService = absenceService;
        this.scheduleService = scheduleService;
    }

    private final AppointmentService appointmentService;

    private final DoctorService doctorService;

    private final AbsenceService absenceService;

    private final ScheduleService scheduleService;

    @GetMapping("/doctors/get")
    public List<AdminDoctorDTO> getAllDoctors() {
        return doctorService.getDoctorsAdmin();
    }
    @PostMapping("/doctors/create")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/doctors/{id}")
    public AdminDoctorDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctorDetails) {
        Doctor updatedDoctor = doctorService.updateDoctor(Math.toIntExact(id), doctorDetails);
        return ResponseEntity.ok(updatedDoctor);
    }

    @GetMapping("/absences/get")
    public List<AbsenceDTO> getAllAbsences() {
        return absenceService.getAllAbsences();
    }
    @GetMapping("/absences/{id}")
    public AbsenceDTO getAbsenceById(@PathVariable Integer id) {
        return absenceService.getAbsenceById(id);
    }

    @ResponseBody
    @PostMapping("/absences/")
    public ResponseEntity<AbsenceDTO> createAbsence(@RequestBody AbsenceDTO absenceDetails) {
        Absence createdAbsence = absenceService.createAbsence(absenceDetails);
        AbsenceDTO responseDTO = new AbsenceDTO(
                createdAbsence.getDoctor().getId(),
                createdAbsence.getId(),
                createdAbsence.getStart_date(),
                createdAbsence.getEnd_date(),
                createdAbsence.getAbsence_reason()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/absences/{id}")
    public ResponseEntity<Absence> updateAbsence(
            @PathVariable Integer id,
            @RequestBody Absence absenceDetails) {
        Absence updatedAbsence = absenceService.updateAbsence(id, absenceDetails);
        return ResponseEntity.ok(updatedAbsence);
    }

    @DeleteMapping("/absences/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Integer id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }

@GetMapping("/appointment/get")
public ResponseEntity<List<AdminAppointmentDTO>> getAllAppointments() {
    return ResponseEntity.ok(appointmentService.getAllAppointments());
}
    @GetMapping("/appointment/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);
        return appointmentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/appointment/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Integer id,
            @RequestBody Appointment appointmentDetails) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
        return ResponseEntity.ok(updatedAppointment);
    }
    @PostMapping("/appointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @ResponseBody
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules/get")
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> schedule = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedule);
    }
    @PutMapping("/schedules/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Integer id,
            @RequestBody Schedule scheduleDetails) {
        Schedule updatedSchedule = scheduleService.updateSchedule(id, scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Integer id) {
        ScheduleDTO schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }


    @ResponseBody
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO ScheduleDetails) {
        Schedule createdSchedule = scheduleService.createSchedule(ScheduleDetails);
        ScheduleDTO responseDTO = new ScheduleDTO(
                createdSchedule.getDoctor().getId(),
                createdSchedule.getId(),
                createdSchedule.getDay_of_week(),
                createdSchedule.getStart_time(),
                createdSchedule.getEnd_time(),
                createdSchedule.getValid_to()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
