package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyOperationService implements ExchangeApiConstants {

    public Double valueConversion(Double amountFrom, Double exRate){
        return amountFrom*exRate;
    }

    public Map<String,Double> allRatesFromCurrency(String from, Map<String,Double> rates){
        final Double baseCurrency = rates.get(from);
        rates.replaceAll((code,rate) -> rateFromCurrency(rate,baseCurrency));
        return rates;
    }

    public Double rateFromCurrency(Double from, Double to){
        return from/to;
    }
}
