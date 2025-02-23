package ec.sergy.springtest.service;

import ec.sergy.springtest.dto.RegisterRequest;
import ec.sergy.springtest.dto.RegisterResponse;
import ec.sergy.springtest.model.Cliente;
import ec.sergy.springtest.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public RegisterResponse registerUser(RegisterRequest request) {

        if (clienteRepository.findByClientId(request.getUsername()) != null) {
            return new RegisterResponse("El usuario ya existe");
        }


        Cliente cliente = new Cliente();
        cliente.setClientId(request.getUsername());
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setState(true);

        cliente.setName(request.getName());
        cliente.setGender(request.getGender());
        cliente.setAge(request.getAge());
        cliente.setIdentification(request.getIdentification());
        cliente.setAddress(request.getAddress());
        cliente.setPhone(request.getPhone());


        clienteRepository.save(cliente);

        return new RegisterResponse("Usuario registrado correctamente");
    }
}
