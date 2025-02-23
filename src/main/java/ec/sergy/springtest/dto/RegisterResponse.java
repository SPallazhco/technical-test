package ec.sergy.springtest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }

}