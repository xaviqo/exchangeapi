package tech.xavi.exchangeapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.exchangeapi.dto.rest.AllRatesFromCurrencyDto;
import tech.xavi.exchangeapi.dto.rest.ExchangeRateFromCurrencyDto;
import tech.xavi.exchangeapi.service.ExchangeRateService;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    @GetMapping("/ex-rate/{from}/{to}")
    public ResponseEntity<ExchangeRateFromCurrencyDto> getOneExchangeFromCurrency(@PathVariable String from, @PathVariable String to){
        return ResponseEntity.ok(
                exchangeRateService.getExchangeRateFromCurrency(
                        from.toUpperCase(),
                        to.toUpperCase()
                )
        );
    }

    @GetMapping("/ex-rate/{from}")
    public ResponseEntity<AllRatesFromCurrencyDto> getAllExchangeFromCurrency(@PathVariable String from){
        return ResponseEntity.ok(
                exchangeRateService.getAllRatesFromCurrency(from.toUpperCase())
        );
    }

}
