package tech.xavi.exchangeapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.rest.AllRatesFromCurrencyDto;
import tech.xavi.exchangeapi.dto.rest.ExchangeRateFromCurrencyDto;
import tech.xavi.exchangeapi.model.ExchangeError;
import tech.xavi.exchangeapi.service.CurrencySymbolService;
import tech.xavi.exchangeapi.service.ExchangeRateService;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final CurrencySymbolService currencySymbolService;

    @GetMapping("/ex-rate/{from}/{to}")
    public ResponseEntity<ExchangeRateFromCurrencyDto> getOneExchangeFromCurrency(@PathVariable String from, @PathVariable String to){
        if (!currencySymbolService.isCurrencyAvailable(from,to))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                exchangeRateService.getExchangeRateFromCurrency(
                        from.toUpperCase(),
                        to.toUpperCase()
                )
        );
    }

    @GetMapping("/ex-rate/{from}")
    public ResponseEntity<AllRatesFromCurrencyDto> getAllExchangeFromCurrency(@PathVariable String from){
        if (!currencySymbolService.isCurrencyAvailable(from))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                exchangeRateService.getAllRatesFromCurrency(from.toUpperCase())
        );
    }

}
