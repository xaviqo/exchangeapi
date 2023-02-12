package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyOperationService implements ExchangeApiConstants {

    public Map<String,Double> multipleValueConversion(Double amountTo, Map<String,Double> rates){
        rates.replaceAll((code,rate) -> valueConversion(amountTo,rate));
        return rates;
    }

    public Double valueConversion(Double amountTo, Double exRate){
        return amountTo*exRate;
    }

    public Map<String,Double> allRatesFromCurrency(String from, Map<String,Double> rates){
        final Double baseCurrency = rates.get(from);
        rates.replaceAll((code,rate) -> rate/baseCurrency);
        return rates;
    }

    public Double rateFromCurrency(Double from, Double to){
        return to/from;
    }
}
