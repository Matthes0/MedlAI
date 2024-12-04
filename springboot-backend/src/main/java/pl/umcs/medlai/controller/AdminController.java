package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.umcs.medlai.model.Absence;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.model.Status;
import pl.umcs.medlai.service.AbsenceService;
import pl.umcs.medlai.service.AppointmentService;
import pl.umcs.medlai.service.DoctorService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppointmentService appointmentService;

    private DoctorService doctorService;

    @Autowired
    private AbsenceService absenceService;

    @PostMapping("/doctors/{doctorId}/absence")
    public ResponseEntity<String> setDoctorAbsence(
            @PathVariable Integer doctorId,
            @RequestBody Absence absenceDetails) {
        absenceService.setDoctorAbsence(doctorId, absenceDetails);
        return ResponseEntity.ok("Absence added successfully for doctor with ID " + doctorId);
    }

    @PostMapping("/doctors")
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        return ResponseEntity.ok("Doctor added successfully.");
    }
    @PutMapping("/doctors/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        doctorService.updateDoctor(id, doctorDetails);
        return ResponseEntity.ok("Doctor updated successfully.");
    }
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        boolean hasAppointments = doctorService.hasFutureAppointments(id);
        if (hasAppointments) {
            return ResponseEntity.badRequest().body("Doctor has future appointments. Please cancel them first.");
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
    @PutMapping("/appointments/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(
            @PathVariable Integer appointmentId,
            @RequestParam Status newStatus) {
        appointmentService.updateAppointmentStatus(appointmentId, newStatus);
        return ResponseEntity.ok("Appointment status updated to " + newStatus);
    }
//    @DeleteMapping("/appointments/{id}")
//    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
//        appointmentService.cancelAppointment(id);
//        return ResponseEntity.ok("Appointment canceled successfully.");
//    }

}
