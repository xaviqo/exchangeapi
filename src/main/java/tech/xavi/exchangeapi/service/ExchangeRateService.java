package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.exrate.AllRatesDto;
import tech.xavi.exchangeapi.dto.rest.exrate.ExchangeRateDto;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeRateService implements ExchangeApiConstants {

    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public ExchangeRateDto getExchangeRateFromCurrency(String from, String to){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        return ExchangeRateDto.builder()
                .from(from)
                .to(to)
                .exchangeRate(operationService.rateFromCurrency(rates.get(from),rates.get(to)))
                .baseCurrency(BASE_CURRENCY)
                .date(LocalDateTime.now())
                .build();
    }

    public AllRatesDto getAllRatesFromCurrency(String from){
        final Map<String,Double> rates = callService.getRequestedRates(ALL_RATES);
        return AllRatesDto.builder()
                .from(from)
                .exchangeRates(operationService.allRatesFromCurrency(from,rates))
                .baseCurrency(BASE_CURRENCY)
                .date(LocalDateTime.now())
                .build();
    }


}
