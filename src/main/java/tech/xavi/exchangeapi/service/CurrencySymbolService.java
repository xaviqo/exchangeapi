package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponse;
import tech.xavi.exchangeapi.integration.IntegrationCall;
import tech.xavi.exchangeapi.mapper.ResponseMapper;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CurrencySymbolService implements ExchangeApiConstants {

    private final IntegrationCall integrationCall;
    private final ResponseMapper responseMapper;

    public boolean isCurrencyAvailable(String... requestedCurrencies){
        final Set<String> isoCodes = getAllIsoCodes();
        for (String currency : requestedCurrencies) {
            if (!isoCodes.contains(currency.toUpperCase())) return false;
        }
        return true;
    }

    private Set<String> getAllIsoCodes(){
        final Set<String> allSymbols = new HashSet<>();
        availableCurrencies().getSymbols().forEach( (iso, currencyItem) -> {
            allSymbols.add(iso);
        });
        return allSymbols;
    }

    private AvailableSymbolsResponse availableCurrencies(){
        return responseMapper.toAvailableSymbols(
                integrationCall.callEndpoint(SUPPORTED_SYMBOLS)
        );
    }
}
