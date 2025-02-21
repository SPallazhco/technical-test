package ec.sergy.sofka.dto;

import lombok.Getter;

@Getter
public class JwtResponse {
    // Getter
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
