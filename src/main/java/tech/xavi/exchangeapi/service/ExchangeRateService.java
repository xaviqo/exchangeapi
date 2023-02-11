package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.AllRatesFromCurrencyDto;
import tech.xavi.exchangeapi.dto.rest.ExchangeRateFromCurrencyDto;

import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeRateService implements ExchangeApiConstants {

    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public ExchangeRateFromCurrencyDto getExchangeRateFromCurrency(String from, String to){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        return ExchangeRateFromCurrencyDto.builder()
                .from(from)
                .to(to)
                .exchangeRate(operationService.calculateRateFromCurrency(rates.get(from),rates.get(to)))
                .build();
    }

    public AllRatesFromCurrencyDto getAllRatesFromCurrency(String from){
        return AllRatesFromCurrencyDto.builder()
                .from(from)
                .exchangeRates(operationService.calculateAllRatesFromCurrency(from))
                .build();
    }


}
