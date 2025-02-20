package ec.sergy.sofka.controller;

import ec.sergy.sofka.dto.JwtResponse;
import ec.sergy.sofka.dto.LoginRequest;
import ec.sergy.sofka.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        return authenticationService.createAuthenticationToken(loginRequest);
    }

}
