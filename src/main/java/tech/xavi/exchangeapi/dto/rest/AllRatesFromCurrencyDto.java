package tech.xavi.exchangeapi.dto.rest;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AllRatesFromCurrencyDto {

    private String from;
    private Map<String,Double> exchangeRates;
}
