package ec.sergy.sofka.controller;

import ec.sergy.sofka.dto.RegisterRequest;
import ec.sergy.sofka.dto.RegisterResponse;
import ec.sergy.sofka.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest request) {
        return registrationService.registerUser(request);
    }

}