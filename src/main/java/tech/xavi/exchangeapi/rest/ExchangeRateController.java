package tech.xavi.exchangeapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.rest.exrate.AllRatesResDTO;
import tech.xavi.exchangeapi.dto.rest.exrate.ExchangeRateResDTO;
import tech.xavi.exchangeapi.model.ExchangeError;
import tech.xavi.exchangeapi.service.CurrencySymbolService;
import tech.xavi.exchangeapi.service.ExchangeRateService;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final CurrencySymbolService currencySymbolService;

    @GetMapping("/exchange-rate/{from:[a-zA-Z]{3}}/{to:[a-zA-Z]{3}}")
    public ResponseEntity<ExchangeRateResDTO> getOneExchangeFromCurrency(
            @PathVariable String from,
            @PathVariable String to)
    {
        if (!currencySymbolService.isCurrencyAvailable(from,to))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                exchangeRateService.getExchangeRateFromCurrency(
                        from.toUpperCase(),
                        to.toUpperCase()
                )
        );
    }

    @GetMapping("/all-rates/{from:[a-zA-Z]{3}}")
    public ResponseEntity<AllRatesResDTO> getAllExchangeFromCurrency(@PathVariable String from){
        if (!currencySymbolService.isCurrencyAvailable(from))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                exchangeRateService.getAllRatesFromCurrency(from.toUpperCase())
        );
    }


}
