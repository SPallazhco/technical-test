package ec.sergy.sofka.service;

import ec.sergy.sofka.model.Cuenta;
import ec.sergy.sofka.model.Cliente;
import ec.sergy.sofka.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    private Cuenta cuenta;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Configuramos un cliente de ejemplo
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setClientId("john.doe");
        cliente.setName("John Doe");
        // Otros atributos del cliente pueden configurarse según sea necesario

        // Configuramos una cuenta de ejemplo
        cuenta = new Cuenta();
        cuenta.setId(1);
        cuenta.setAccountNumber("123456");
        cuenta.setAccountType("Savings");
        cuenta.setInitialBalance(new BigDecimal("1000.00"));
        cuenta.setState(true);
        cuenta.setCliente(cliente);
    }

    @Test
    void testCreateCuenta() {
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta createdCuenta = cuentaService.createCuenta(cuenta);
        assertNotNull(createdCuenta);
        assertEquals("123456", createdCuenta.getAccountNumber());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testGetAllCuentas() {
        when(cuentaRepository.findAll()).thenReturn(Arrays.asList(cuenta));

        List<Cuenta> cuentas = cuentaService.getAllCuentas();
        assertNotNull(cuentas);
        assertFalse(cuentas.isEmpty());
        assertEquals(1, cuentas.size());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    void testGetCuentaById() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> foundCuenta = cuentaService.getCuentaById(1);
        assertTrue(foundCuenta.isPresent());
        assertEquals("123456", foundCuenta.get().getAccountNumber());
        verify(cuentaRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateCuenta() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta cuentaDetails = new Cuenta();
        cuentaDetails.setAccountNumber("654321");
        cuentaDetails.setAccountType("Checking");
        cuentaDetails.setInitialBalance(new BigDecimal("1500.00"));
        cuentaDetails.setState(false);
        // Se asocia el mismo cliente para simplificar
        cuentaDetails.setCliente(cliente);

        Cuenta updatedCuenta = cuentaService.updateCuenta(1, cuentaDetails);

        assertNotNull(updatedCuenta);
        assertEquals("654321", updatedCuenta.getAccountNumber());
        assertEquals("Checking", updatedCuenta.getAccountType());
        assertEquals(new BigDecimal("1500.00"), updatedCuenta.getInitialBalance());
        assertFalse(updatedCuenta.getState());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testDeleteCuenta() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        cuentaService.deleteCuenta(1);
        verify(cuentaRepository, times(1)).delete(cuenta);
    }

    @Test
    void testDeleteCuenta_NotFound() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> cuentaService.deleteCuenta(1));
        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaRepository, never()).delete(any(Cuenta.class));
    }
}