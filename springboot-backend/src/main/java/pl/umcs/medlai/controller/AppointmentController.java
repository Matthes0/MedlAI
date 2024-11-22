package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.umcs.medlai.dao.AppointmentDAO;
import pl.umcs.medlai.model.Appointment;
import pl.umcs.medlai.service.AppointmentService;

import java.util.Optional;

@Controller
@RequestMapping(path = "/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "appointment-form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Appointment appointment) {
        this.appointmentService.saveOrUpdate(appointment);
        return "redirect:/main";
    }
    // later to change id to jwt
    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        Optional<Appointment> appointmentOpt = this.appointmentService.getById(id);
        if(appointmentOpt.isEmpty()) {
            return "redirect:/main";
        }
        model.addAttribute("appointment", appointmentOpt.get());
        return "appointment-form";
    }
    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, @ModelAttribute Appointment appointment) {
        //appointment.setId(id);
        this.appointmentService.saveOrUpdate(appointment);
        return "redirect:/main";
    }
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String deleteAppointment(@RequestParam long id) {
        appointmentService.delete(id);
        return "redirect:/main";
    }
}