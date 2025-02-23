package ec.sergy.springtest.repository;

import ec.sergy.springtest.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :accountId AND m.date BETWEEN :startDate AND :endDate")
    List<Movimiento> findByAccountAndDateRange(
            @Param("accountId") Integer accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :cuentaId ORDER BY m.date ASC")
    List<Movimiento> findByCuentaId(@Param("cuentaId") Integer cuentaId);

}