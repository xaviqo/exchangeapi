package tech.xavi.exchangeapi.dto.rest.exrate;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class AllRatesResDTO {

    private String from;
    private Map<String,Double> exchangeRates;
    private String baseExchangeCurrency;
    private LocalDateTime date;

}
