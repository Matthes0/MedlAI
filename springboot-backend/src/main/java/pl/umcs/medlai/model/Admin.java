package pl.umcs.medlai.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Admin")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}