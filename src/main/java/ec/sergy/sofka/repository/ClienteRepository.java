package ec.sergy.sofka.repository;

import ec.sergy.sofka.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByClientId(String clientId);
}