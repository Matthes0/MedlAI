package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Doctor")
public class Doctor {
    @Id
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private String first_name;
    @Setter
    @Getter
    private String last_name;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String phone;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    @Getter
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    @Getter
    private List<Schedule> schedule;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    @Getter
    private List<Absence> absence;

}
