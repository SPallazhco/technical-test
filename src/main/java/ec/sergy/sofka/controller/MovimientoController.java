package ec.sergy.sofka.controller;

import ec.sergy.sofka.dto.TransactionReportRequest;
import ec.sergy.sofka.model.Movimiento;
import ec.sergy.sofka.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;


    @GetMapping("/{cuentaId}")
    public List<Movimiento> getMovimientosByCuenta(@PathVariable Integer cuentaId) {
        return movimientoService.getMovimientosByCuenta(cuentaId);
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento) {
        return movimientoService.createMovimiento(movimiento);
    }

    @PutMapping("/{id}")
    public Movimiento updateMovimiento(@PathVariable Integer id, @RequestBody Movimiento movimientoDetails) {
        return movimientoService.updateMovimiento(id, movimientoDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimiento(@PathVariable Integer id) {
        movimientoService.deleteMovimiento(id);
    }


    @PostMapping("/reporte")
    public List<Movimiento> getMovementsReport(@RequestBody TransactionReportRequest request) {
        return movimientoService.getMovementsReport(request);
    }
}