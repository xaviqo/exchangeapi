package tech.xavi.exchangeapi.dto.rest.exrate;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExchangeRateResDTO {

    private String from;
    private String to;
    private Double exchangeRate;
    private String baseExchangeCurrency;
    private LocalDateTime date;

}
