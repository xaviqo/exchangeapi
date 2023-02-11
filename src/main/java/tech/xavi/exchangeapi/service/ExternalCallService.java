package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
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
