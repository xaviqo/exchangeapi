package tech.xavi.exchangeapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.rest.valueconversion.ValueConversionDto;
import tech.xavi.exchangeapi.model.ExchangeError;
import tech.xavi.exchangeapi.service.CurrencySymbolService;
import tech.xavi.exchangeapi.service.ValueConversionService;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ValueConversionController {

    private final ValueConversionService conversionService;
    private final CurrencySymbolService currencySymbolService;
    @GetMapping("/val-conversion/{from:[a-zA-Z]{3}}/{to:[a-zA-Z]{3}}/{amount}")
    public ResponseEntity<ValueConversionDto> getOneValueConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable Double amount)
    {
        if (!currencySymbolService.isCurrencyAvailable(from,to))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                conversionService.getValueConversion(from.toUpperCase(),to.toUpperCase(),amount)
        );
    }
}
