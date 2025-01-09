package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.dao.DoctorDAO;
import pl.umcs.medlai.dto.AppointmentBookedDTO;
import pl.umcs.medlai.dto.AppointmentConfirmationDTO;
import pl.umcs.medlai.dto.AppointmentDTO;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.service.AppointmentService;
import pl.umcs.medlai.service.DoctorService;
import pl.umcs.medlai.service.EmailService;
import pl.umcs.medlai.model.Status;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private DoctorDAO doctorDAO;

    @ResponseBody
    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
    public AppointmentBookedDTO add(@RequestBody AppointmentBookedDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setDoctor((this.doctorDAO.getById(appointmentDTO.getDoctor_id())).get());
        appointment.setStart_date(appointmentDTO.getStart_date());
        appointment.setPatient_first_name(appointmentDTO.getPatient_first_name());
        appointment.setPatient_last_name(appointmentDTO.getPatient_last_name());
        appointment.setPatient_email(appointmentDTO.getPatient_email());
        appointment.setPatient_phone(appointmentDTO.getPatient_phone());
        appointment.setPatient_address(appointmentDTO.getPatient_address());
        appointment.setPatient_pesel(appointmentDTO.getPatient_pesel());
        appointment.setStatus(appointmentDTO.getStatus());
        this.appointmentService.saveOrUpdate(appointment);
        //this.emailService.sendEmail(appointment.getPatient_email(), "Potwierdź wizytę w przychodni MedlAI", "Potwierdź mail pls <br> to link: " + this.emailService.generateAppointmentLink(appointment) + "<br> Miłej wizyty, MedlAI team");
        System.out.println(this.emailService.generateAppointmentLink(appointment));
        return appointmentDTO;
    }

    //    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
//    public String update(@PathVariable Integer id, Model model) {
//        Optional<Appointment> appointmentOpt = this.appointmentService.getById(id);
//        if(appointmentOpt.isEmpty()) {
//            return "redirect:/main";
//        }
//        model.addAttribute("appointment", appointmentOpt.get());
//        return "appointment-form";
//    }
//    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST)
//    public String update(@PathVariable Integer id, @ModelAttribute Appointment appointment) {
//        //appointment.setId(id);
//        this.appointmentService.saveOrUpdate(appointment);
//        return "redirect:/main";
//    }
//    @RequestMapping(path = "/delete", method = RequestMethod.POST)
//    public String deleteAppointment(@RequestParam Integer id) {
//        appointmentService.delete(id);
//        return "redirect:/main";
//    }
    @GetMapping("/get")
    public List<AppointmentDTO> getAvailableAppointments(@RequestParam("doctorID") Integer id, @RequestParam("date") String date) {
        System.out.println(date);
        return appointmentService.generateAvailableAppointments(id, date);
    }

    @GetMapping("/getconfirmation")
    public AppointmentConfirmationDTO getConfirmAppointment(@RequestParam String token) {
        System.out.println("to jest token otrzymany w gecie " + token);
        Integer appointmentId = emailService.validateAppointmentLink(token);
        System.out.println(appointmentId);
        Optional<Appointment> optionalAppointment = appointmentService.getById(appointmentId);
        AppointmentConfirmationDTO appointmentConfirmationDTO = null;
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointmentConfirmationDTO = new AppointmentConfirmationDTO(appointment.getDoctor().getFirst_name(), appointment.getDoctor().getLast_name(), appointment.getStart_date(), appointment.getPatient_first_name(), appointment.getPatient_last_name(), appointment.getPatient_email(), appointment.getPatient_phone(), appointment.getPatient_pesel(), appointment.getPatient_address());
        }
        return appointmentConfirmationDTO;
    }

    @ResponseBody
    @RequestMapping(path = "/confirm", method = RequestMethod.POST, consumes = "application/json")
    public String confirmAppointment(@RequestBody String token) {
        String result = token.substring(1, token.length() - 1);
        System.out.println("to jest token otrzymany w poscie " + token);
        Integer appointmentId = emailService.validateAppointmentLink(result);
        System.out.println(appointmentId);
        Optional<Appointment> optionalAppointment = appointmentService.getById(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            System.out.println(appointment.getStatus());
            appointment.setStatus(Status.SCHEDULED);
            System.out.println(appointment.getStatus());
            appointmentService.saveOrUpdate(appointment);
        }
        return token;
    }
}
