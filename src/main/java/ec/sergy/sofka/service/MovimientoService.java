package ec.sergy.sofka.service;

import ec.sergy.sofka.dto.TransactionReportRequest;
import ec.sergy.sofka.exception.CustomException;
import ec.sergy.sofka.model.Cuenta;
import ec.sergy.sofka.model.Movimiento;
import ec.sergy.sofka.repository.CuentaRepository;
import ec.sergy.sofka.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento createMovimiento(Movimiento movimiento) {

        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new CustomException("Cuenta no encontrada"));

        if (!Boolean.TRUE.equals(cuenta.getState())) {
            throw new CustomException("No se pueden realizar movimientos en una cuenta inactiva");
        }

        if (!movimiento.getMovementType().equalsIgnoreCase("DEPOSIT") &&
                !movimiento.getMovementType().equalsIgnoreCase("WITHDRAWAL")) {
            throw new CustomException("Tipo de movimiento no válido. Debe ser DEPOSIT o WITHDRAWAL.");
        }

        BigDecimal nuevoSaldo;
        if (movimiento.getMovementType().equalsIgnoreCase("WITHDRAWAL")) {
            nuevoSaldo = cuenta.getInitialBalance().subtract(movimiento.getValue().abs());
        } else {
            nuevoSaldo = cuenta.getInitialBalance().add(movimiento.getValue());
        }

        if (movimiento.getMovementType().equalsIgnoreCase("WITHDRAWAL") && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException("Saldo insuficiente para realizar el retiro");
        }

        cuenta.setInitialBalance(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setBalance(nuevoSaldo);
        movimiento.setDate(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> getMovimientosByCuenta(Integer cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    @Transactional
    public void deleteMovimiento(Integer movimientoId) {
        Movimiento movimiento = movimientoRepository.findById(movimientoId)
                .orElseThrow(() -> new CustomException("Movimiento no encontrado"));

        Cuenta cuenta = movimiento.getCuenta();

        List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuenta.getId());

        if (movimientos.isEmpty()) {
            throw new CustomException("No hay movimientos registrados para esta cuenta");
        }

        Movimiento ultimoMovimiento = movimientos.get(movimientos.size() - 1);

        if (!ultimoMovimiento.getId().equals(movimientoId)) {
            throw new CustomException("Solo se puede eliminar el último movimiento registrado");
        }

        // Revertir el saldo de la cuenta
        cuenta.setInitialBalance(cuenta.getInitialBalance().subtract(movimiento.getValue()));
        cuentaRepository.save(cuenta);

        // Eliminar el movimiento
        movimientoRepository.delete(movimiento);
    }

    public Movimiento updateMovimiento(Integer id, Movimiento movimientoDetails) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Movimiento no encontrado"));

        movimiento.setDate(movimientoDetails.getDate());
        movimiento.setMovementType(movimientoDetails.getMovementType());
        movimiento.setValue(movimientoDetails.getValue());
        movimiento.setBalance(movimientoDetails.getBalance());
        movimiento.setCuenta(movimientoDetails.getCuenta());

        return movimiento;
    }

    public List<Movimiento> getMovementsReport(TransactionReportRequest request) {
        return movimientoRepository.findByAccountAndDateRange(
                request.getAccountId(),
                request.getStartDate(),
                request.getEndDate()
        );
    }
}
