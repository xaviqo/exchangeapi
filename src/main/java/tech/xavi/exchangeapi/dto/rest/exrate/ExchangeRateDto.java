package tech.xavi.exchangeapi.dto.rest.exrate;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExchangeRateDto {

    private String from;
    private String to;
    private Double exchangeRate;
    private String baseCurrency;
    private LocalDateTime date;

}
