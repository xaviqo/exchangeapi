package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponse;
import tech.xavi.exchangeapi.integration.IntegrationCall;
import tech.xavi.exchangeapi.mapper.ResponseMapper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExternalCallService implements ExchangeApiConstants {

    private final IntegrationCall integrationCall;
    private final ResponseMapper responseMapper;
    private final CacheManager cacheManager;

    @Cacheable(value = API_CACHE_NAME)
    public AvailableSymbolsResponse availableCurrencies(){
        return responseMapper.toAvailableSymbols(
                integrationCall.callEndpoint(SUPPORTED_SYMBOLS)
        );
    }

    @Cacheable(value = API_CACHE_NAME, key = "#root.methodName + ':' + " +
            "T(org.springframework.util.StringUtils).arrayToDelimitedString(#arrayOfRates, ',')"
    )
    public Map<String, Double> getRequestedRates(String... arrayOfRates) {
        return Arrays.asList(arrayOfRates).contains(ALL_RATES)
                ? responseMapper.toLatestRates(integrationCall.callEndpoint(LATEST_RATES)).getRates()
                : responseMapper.toLatestRates(integrationCall.callEndpoint(LATEST_RATES)).getRates()
                .entrySet()
                .stream()
                .filter(rate -> Arrays.asList(arrayOfRates).contains(rate.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
