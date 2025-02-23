package ec.sergy.springtest.controller;

import ec.sergy.springtest.dto.JwtResponse;
import ec.sergy.springtest.dto.LoginRequest;
import ec.sergy.springtest.service.AuthenticationService;
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
