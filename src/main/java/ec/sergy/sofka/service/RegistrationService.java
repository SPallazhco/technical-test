package ec.sergy.sofka.service;

import ec.sergy.sofka.dto.RegisterRequest;
import ec.sergy.sofka.dto.RegisterResponse;
import ec.sergy.sofka.model.Cliente;
import ec.sergy.sofka.repository.ClienteRepository;
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
        // Validar que el usuario no exista
        if (clienteRepository.findByClientId(request.getUsername()) != null) {
            return new RegisterResponse("El usuario ya existe");
        }

        // Crear el objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setClientId(request.getUsername());
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setState(true);
        // Asignamos atributos de Persona
        cliente.setName(request.getName());
        cliente.setGender(request.getGender());
        cliente.setAge(request.getAge());
        cliente.setIdentification(request.getIdentification());
        cliente.setAddress(request.getAddress());
        cliente.setPhone(request.getPhone());

        // Guardamos el cliente
        clienteRepository.save(cliente);

        return new RegisterResponse("Usuario registrado correctamente");
    }
}
