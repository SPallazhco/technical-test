package ec.sergy.sofka.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class MovimientoRequest {
    // Puedes incluir la fecha o asignarla automáticamente en el servicio
    private LocalDateTime date;

    private String movementType;

    private BigDecimal value;

    private String accountNumber;  // Este campo reemplaza la referencia a Cuenta

}