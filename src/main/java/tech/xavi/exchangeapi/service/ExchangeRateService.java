package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.exrate.AllRatesResDTO;
import tech.xavi.exchangeapi.dto.rest.exrate.ExchangeRateResDTO;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeRateService implements ExchangeApiConstants {

    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public ExchangeRateResDTO getExchangeRateFromCurrency(String from, String to){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        return ExchangeRateResDTO.builder()
                .from(from)
                .to(to)
                .exchangeRate(operationService.rateFromCurrency(rates.get(from),rates.get(to)))
                .baseExchangeCurrency(BASE_CURRENCY)
                .date(LocalDateTime.now())
                .build();
    }

    public AllRatesResDTO getAllRatesFromCurrency(String from){
        final Map<String,Double> rates = callService.getRequestedRates(ALL_RATES);
        return AllRatesResDTO.builder()
                .from(from)
                .exchangeRates(operationService.allRatesFromCurrency(from,rates))
                .baseExchangeCurrency(BASE_CURRENCY)
                .date(LocalDateTime.now())
                .build();
    }


}
