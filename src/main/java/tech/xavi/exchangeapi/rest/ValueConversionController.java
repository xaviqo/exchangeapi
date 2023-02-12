package tech.xavi.exchangeapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionReqDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionResDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.ValueConversionResDTO;
import tech.xavi.exchangeapi.model.ExchangeError;
import tech.xavi.exchangeapi.service.CurrencySymbolService;
import tech.xavi.exchangeapi.service.ValueConversionService;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ValueConversionController {

    private final ValueConversionService conversionService;
    private final CurrencySymbolService currencySymbolService;
    @GetMapping("/value-conversion/{from:[a-zA-Z]{3}}/{to:[a-zA-Z]{3}}/{amountTo}")
    public ResponseEntity<ValueConversionResDTO> getOneValueConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable Double amountTo)
    {
        if (!currencySymbolService.isCurrencyAvailable(from,to))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                conversionService.getValueConversion(from.toUpperCase(),to.toUpperCase(),amountTo)
        );
    }

    @PostMapping("/multiple-values-conversion")
    public ResponseEntity<MultipleValueConversionResDTO> getMultipleValueConversion(
            @RequestBody MultipleValueConversionReqDTO multipleValueConversionReq)
    {
        if (!currencySymbolService.isCurrencyAvailable(multipleValueConversionReq.getFrom()))
            throw new ExchangeApiException(ExchangeError.INVALID_CURRENCY, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(
                conversionService.getMultipleValueConversion(multipleValueConversionReq)
        );
    }


}
