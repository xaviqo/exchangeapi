package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyOperationService implements ExchangeApiConstants {

    private final ExternalCallService callService;

    public Map<String,Double> calculateAllRatesFromCurrency(String from){
        final Map<String,Double> rates = callService.getRequestedRates(ALL_RATES);
        final Double baseCurrency = rates.get(from);
        rates.replaceAll((code,rate) -> rate/baseCurrency);
        return rates;
    }

    public Double calculateRateFromCurrency(Double from, Double to){
        return from/to;
    }
}
