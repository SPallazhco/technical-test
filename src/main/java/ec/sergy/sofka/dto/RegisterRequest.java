package ec.sergy.sofka.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    // Getters y Setters
    private String username;  // Se utilizará como clientId
    private String password;
    private String name;      // Nombre de la persona
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}