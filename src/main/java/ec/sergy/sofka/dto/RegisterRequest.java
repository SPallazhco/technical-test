package ec.sergy.sofka.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

    private String username;
    private String password;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}