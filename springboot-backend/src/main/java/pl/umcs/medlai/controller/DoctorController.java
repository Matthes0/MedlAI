package pl.umcs.medlai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.medlai.dto.DoctorDTO;
import pl.umcs.medlai.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/get")
    public List<DoctorDTO> getDoctors()
    {
        List<DoctorDTO> doctors = doctorService.getAll();
        // System.out.println(doctors);
        return doctors;
    }

}
