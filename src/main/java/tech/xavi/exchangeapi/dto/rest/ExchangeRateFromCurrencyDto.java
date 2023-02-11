package tech.xavi.exchangeapi.dto.rest;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Builder
public class ExchangeRateFromCurrencyDto {

    private String from;
    private String to;
    private Double exchangeRate;

}
