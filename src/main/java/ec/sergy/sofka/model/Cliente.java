package ec.sergy.sofka.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "persona_id") // Esta columna es la PK y FK que hace referencia a persona.id
public class Cliente extends Persona {

    // Ya no se define id aquí, se hereda de Persona

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String password;

    private Boolean state;

    // Getters y Setters

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}