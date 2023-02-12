package tech.xavi.exchangeapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionReqDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionResDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.ValueConversionResDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValueConversionService implements ExchangeApiConstants {

    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public MultipleValueConversionResDTO getMultipleValueConversion(
            MultipleValueConversionReqDTO multipleValueConversionReq)
    {
        // TODO: Si no hace falta el fromCurrencyDate, refactorizar.
        final Map<String,Double> allRates = callService.getRequestedRates(ALL_RATES);
        //final Double fromCurrencyRate = allRates.get(multipleValueConversionReq.getFrom().toUpperCase());
        final Map<String,Double> targetRates = allRates
                .entrySet()
                .stream()
                .filter(rate -> multipleValueConversionReq.getTargetCurrencies()
                        .stream()
                        .map(String::toUpperCase)
                        .toList()
                        .contains(rate.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return MultipleValueConversionResDTO.builder()
                .from(multipleValueConversionReq.getFrom().toUpperCase())
                .amountTo(multipleValueConversionReq.getAmountTo())
                .targetConversions(
                        operationService.multipleValueConversion(
                                multipleValueConversionReq.getAmountTo(),
                                targetRates))
                .baseExchangeCurrency(BASE_CURRENCY)
                .date(LocalDateTime.now())
                .build();
    }

    public ValueConversionResDTO getValueConversion(String from, String to, Double amountTo){
        final Map<String,Double> rates = callService.getRequestedRates(from,to);
        final Double exchangeRate = operationService.rateFromCurrency(rates.get(to),rates.get(from));

        return ValueConversionResDTO.builder()
                .from(from)
                .amountFrom(operationService.valueConversion(amountTo,exchangeRate))
                .to(to)
                .amountTo(amountTo)
                .baseExchangeCurrency(BASE_CURRENCY)
                .exchangeRate(exchangeRate)
                .date(LocalDateTime.now())
                .build();
    }
}

