package ec.sergy.sofka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Este id se usará en la tabla persona

    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}