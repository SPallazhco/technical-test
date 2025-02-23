package ec.sergy.springtest.repository;

import ec.sergy.springtest.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    boolean existsByAccountNumber(String accountNumber);
    Optional<Cuenta> findByAccountNumber(String accountNumber);
}