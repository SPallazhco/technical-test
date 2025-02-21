package ec.sergy.sofka.service;

import ec.sergy.sofka.dto.MovimientoRequest;
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

    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAWAL = "WITHDRAWAL";

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento createMovimiento(MovimientoRequest movimientoRequest) {

        // Buscar la cuenta por accountNumber
        Cuenta cuenta = cuentaRepository.findByAccountNumber(movimientoRequest.getAccountNumber())
                .orElseThrow(() -> new CustomException("Cuenta no encontrada"));

        if (!Boolean.TRUE.equals(cuenta.getState())) {
            throw new CustomException("No se pueden realizar movimientos en una cuenta inactiva");
        }

        if (!movimientoRequest.getMovementType().equalsIgnoreCase(DEPOSIT) &&
                !movimientoRequest.getMovementType().equalsIgnoreCase(WITHDRAWAL)) {
            throw new CustomException("Tipo de movimiento no válido. Debe ser DEPOSIT o WITHDRAWAL.");
        }

        // WITHDRAWAL o DEPOSIT
        BigDecimal nuevoSaldo = getBigDecimal(movimientoRequest, cuenta);

        // Actualizamos el saldo de la cuenta
        cuenta.setInitialBalance(nuevoSaldo);
        cuentaRepository.save(cuenta);


        Movimiento movimiento = new Movimiento();
        movimiento.setMovementType(movimientoRequest.getMovementType());
        movimiento.setValue(movimientoRequest.getValue());
        movimiento.setBalance(nuevoSaldo);
        // Si no se envía la fecha en el request, asignamos la fecha actual
        movimiento.setDate(movimientoRequest.getDate() != null ? movimientoRequest.getDate() : LocalDateTime.now());
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

    private static BigDecimal getBigDecimal(MovimientoRequest movimientoRequest, Cuenta cuenta) {
        BigDecimal nuevoSaldo;
        if (movimientoRequest.getMovementType().equalsIgnoreCase(WITHDRAWAL)) {
            nuevoSaldo = cuenta.getInitialBalance().subtract(movimientoRequest.getValue().abs());
        } else {
            nuevoSaldo = cuenta.getInitialBalance().add(movimientoRequest.getValue());
        }
        // Debo validar el saldo negativo Si el saldo es negativo no debo continuar
        if (movimientoRequest.getMovementType().equalsIgnoreCase(WITHDRAWAL) && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException("Saldo insuficiente para realizar el retiro");
        }
        return nuevoSaldo;
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
