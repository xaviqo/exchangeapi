package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.AllRatesFromCurrencyDto;
import tech.xavi.exchangeapi.dto.rest.ExchangeRateFromCurrencyDto;
import tech.xavi.exchangeapi.integration.IntegrationCall;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExchangeRateService implements ExchangeApiConstants {

    private final IntegrationCall integrationCall;

    public ExchangeRateFromCurrencyDto getExchangeRateFromCurrency(String from, String to){
        final Map<String,Double> rates = getRequestedRates(from,to);
        return ExchangeRateFromCurrencyDto.builder()
                .from(from)
                .to(to)
                .exchangeRate(calculateRate(rates.get(from),rates.get(to)))
                .build();
    }

    public AllRatesFromCurrencyDto getAllRatesFromCurrency(String from){
        return AllRatesFromCurrencyDto.builder()
                .from(from)
                .exchangeRates(calculateAllRatesFromCurrency(from))
                .build();
    }

    private Map<String,Double> calculateAllRatesFromCurrency(String from){
        final Map<String,Double> rates = getRequestedRates(ALL_RATES);
        final Double baseCurrency = rates.get(from);
        rates.replaceAll((code,rate) -> rate/baseCurrency);
        return rates;
    }

    private Double calculateRate(Double from, Double to){
        return from/to;
    }

    private Map<String, Double> getRequestedRates(String... arrayOfRates) {
        return Arrays.asList(arrayOfRates).contains(ALL_RATES)
                ? integrationCall.requestCurrentRates().getRates()
                : integrationCall.requestCurrentRates().getRates().entrySet()
                .stream()
                .filter(rate -> Arrays.asList(arrayOfRates).contains(rate.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
