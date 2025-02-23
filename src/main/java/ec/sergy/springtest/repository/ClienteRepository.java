package ec.sergy.springtest.repository;

import ec.sergy.springtest.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByClientId(String clientId);
}