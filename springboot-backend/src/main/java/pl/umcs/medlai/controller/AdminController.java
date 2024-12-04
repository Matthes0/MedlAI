package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.umcs.medlai.model.Doctor;
import pl.umcs.medlai.service.AppointmentService;
import pl.umcs.medlai.service.DoctorService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppointmentService appointmentService;

    private DoctorService doctorService;

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
//    @DeleteMapping("/appointments/{id}")
//    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
//        appointmentService.cancelAppointment(id);
//        return ResponseEntity.ok("Appointment canceled successfully.");
//    }
}
