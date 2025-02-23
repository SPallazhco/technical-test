package ec.sergy.springtest.controller;

import ec.sergy.springtest.model.Cuenta;
import ec.sergy.springtest.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.createCuenta(cuenta);
    }

    @PutMapping("/{id}")
    public Cuenta updateCuenta(@PathVariable Integer id, @RequestBody Cuenta cuentaDetails) {
        return cuentaService.updateCuenta(id, cuentaDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Integer id) {
        cuentaService.deleteCuenta(id);
    }
}