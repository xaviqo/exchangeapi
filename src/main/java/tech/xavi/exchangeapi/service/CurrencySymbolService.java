package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CurrencySymbolService {

    private final ExternalCallService callService;

    public boolean isCurrencyAvailable(String... requestedCurrencies){
        final Set<String> isoCodes = getAllIsoCodes();
        for (String currency : requestedCurrencies) {
            if (!isoCodes.contains(currency.toUpperCase())) return false;
        }
        return true;
    }

    private Set<String> getAllIsoCodes(){
        final Set<String> allSymbols = new HashSet<>();
        callService.availableCurrencies().getSymbols().forEach( (iso, currencyItem) -> {
            allSymbols.add(iso);
        });
        return allSymbols;
    }


}
