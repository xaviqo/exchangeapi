package tech.xavi.exchangeapi.dto.rest.exrate;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class AllRatesDto {

    private String from;
    private Map<String,Double> exchangeRates;
    private String baseCurrency;
    private LocalDateTime date;

}
