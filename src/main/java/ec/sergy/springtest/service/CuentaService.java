package ec.sergy.springtest.service;

import ec.sergy.springtest.exception.CustomException;
import ec.sergy.springtest.model.Cuenta;
import ec.sergy.springtest.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta createCuenta(Cuenta cuenta) {
        if (cuentaRepository.existsByAccountNumber(cuenta.getAccountNumber())) {
            throw new CustomException("Cuenta con número " + cuenta.getAccountNumber() + " ya existe");
        }
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> getCuentaById(Integer id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta updateCuenta(Integer id, Cuenta cuentaDetails) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cuenta no encontrada"));

        cuenta.setAccountNumber(cuentaDetails.getAccountNumber());
        cuenta.setAccountType(cuentaDetails.getAccountType());
        cuenta.setInitialBalance(cuentaDetails.getInitialBalance());
        cuenta.setState(cuentaDetails.getState());
        cuenta.setCliente(cuentaDetails.getCliente());

        return cuentaRepository.save(cuenta);
    }

    public void deleteCuenta(Integer id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cuenta no encontrada"));
        cuentaRepository.delete(cuenta);
    }
}
