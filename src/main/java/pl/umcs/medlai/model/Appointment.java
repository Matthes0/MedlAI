package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Appointment")
public class Appointment {
    @Id
    @Setter
    @Getter
    private Long id;
    private Date start_date;
    private Date end_date;
    @ManyToOne
    @Setter
    @Getter
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Setter
    @Getter
    private String room;

}
