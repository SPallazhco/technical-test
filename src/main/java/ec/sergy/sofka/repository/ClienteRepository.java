package ec.sergy.sofka.repository;

import ec.sergy.sofka.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Para buscar por clientId (username)
    Cliente findByClientId(String clientId);
}