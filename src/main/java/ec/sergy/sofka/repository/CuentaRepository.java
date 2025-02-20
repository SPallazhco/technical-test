package ec.sergy.sofka.repository;

import ec.sergy.sofka.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

}