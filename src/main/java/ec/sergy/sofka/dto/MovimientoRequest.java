package ec.sergy.sofka.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class MovimientoRequest {

    private LocalDateTime date;

    private String movementType;

    private BigDecimal value;

    private String accountNumber;  // Este campo reemplaza la referencia a Cuenta

}