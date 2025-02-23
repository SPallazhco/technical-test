package ec.sergy.springtest.controller;

import ec.sergy.springtest.dto.RegisterRequest;
import ec.sergy.springtest.dto.RegisterResponse;
import ec.sergy.springtest.service.RegistrationService;
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