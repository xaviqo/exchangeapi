package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.configuration.ApiCachingConfiguration;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponseDTO;
import tech.xavi.exchangeapi.integration.ExchangesRatesIntegration;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExternalCallService {


    private final ExchangesRatesIntegration exchangesRatesIntegration;

    @Cacheable(value = ApiCachingConfiguration.CACHE_NAME)
    public AvailableSymbolsResponseDTO availableCurrencies(){
        return exchangesRatesIntegration.getAllSymbols();
    }


    @Cacheable(value = ApiCachingConfiguration.CACHE_NAME, key = "#root.methodName + ':' + " +
            "T(org.springframework.util.StringUtils).arrayToDelimitedString(#arrayOfSymbols, ',')"
    )
    public Map<String, Double> getRequestedRates(String... arrayOfSymbols) {
        return (Arrays.asList(arrayOfSymbols).size() < 1)
                ? exchangesRatesIntegration.getAllRates().getRates()
                : exchangesRatesIntegration.getAllRates().getRates()
                .entrySet()
                .stream()
                .filter(rate -> Arrays.asList(arrayOfSymbols).contains(rate.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
