package ec.sergy.springtest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "movement_type")
    private String movementType;

    @Column
    private BigDecimal value;

    @Column
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Cuenta cuenta;

}