package ec.sergy.sofka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Cliente extends Persona {

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String password;

    private Boolean state;

}