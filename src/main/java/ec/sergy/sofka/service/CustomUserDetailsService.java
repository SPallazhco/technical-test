package ec.sergy.sofka.service;

import ec.sergy.sofka.exception.CustomException;
import ec.sergy.sofka.model.Cliente;
import ec.sergy.sofka.repository.ClienteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByClientId(username);
        if (cliente == null) {
            throw new CustomException("User not found with username: " + username);
        }

        return User.withUsername(cliente.getClientId())
                .password(cliente.getPassword())
                .authorities("USER")
                .build();
    }
}
