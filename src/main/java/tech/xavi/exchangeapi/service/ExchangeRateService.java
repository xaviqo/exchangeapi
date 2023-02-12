package tech.xavi.exchangeapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.dto.rest.exrate.AllRatesResDTO;
import tech.xavi.exchangeapi.dto.rest.exrate.ExchangeRateResDTO;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    @Value("${api.cfg.base-currency}")
    private String baseCurrency;
    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public ExchangeRateResDTO getExchangeRateFromCurrency(String from, String to){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        return ExchangeRateResDTO.builder()
                .from(from)
                .to(to)
                .exchangeRate(operationService.rateFromCurrency(rates.get(from),rates.get(to)))
                .baseExchangeCurrency(baseCurrency)
                .date(LocalDateTime.now())
                .build();
    }

    public AllRatesResDTO getAllRatesFromCurrency(String from){
        final Map<String,Double> rates = callService.getRequestedRates();
        return AllRatesResDTO.builder()
                .from(from)
                .exchangeRates(operationService.allRatesFromCurrency(from,rates))
                .baseExchangeCurrency(baseCurrency)
                .date(LocalDateTime.now())
                .build();
    }


}
