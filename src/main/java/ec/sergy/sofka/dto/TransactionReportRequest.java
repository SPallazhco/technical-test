package ec.sergy.sofka.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionReportRequest {
    private Integer accountId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}