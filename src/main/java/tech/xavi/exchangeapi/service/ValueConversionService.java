package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.valueconversion.ValueConversionDto;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class ValueConversionService implements ExchangeApiConstants {

    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public ValueConversionDto getValueConversion(String from, String to, Double amountFrom){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        final Double exchangeRate = operationService.rateFromCurrency(rates.get(from),rates.get(to));

        return ValueConversionDto.builder()
                .from(from)
                .amountFrom(amountFrom)
                .to(to)
                .amountTo(operationService.valueConversion(amountFrom,exchangeRate))
                .baseCurrency(BASE_CURRENCY)
                .exchangeRate(exchangeRate)
                .date(LocalDateTime.now())
                .build();
    }
}

