package tech.xavi.exchangeapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionReqDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.MultipleValueConversionResDTO;
import tech.xavi.exchangeapi.dto.rest.valueconversion.ValueConversionResDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValueConversionService {

    @Value("${api.cfg.base-currency}")
    private String baseCurrency;
    private final ExternalCallService callService;
    private final CurrencyOperationService operationService;

    public MultipleValueConversionResDTO getMultipleValueConversion(
            MultipleValueConversionReqDTO multipleValueConversionReq)
    {

        final Map<String,Double> targetRates = callService
                .getRequestedRates()
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
                .baseExchangeCurrency(baseCurrency)
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
                .baseExchangeCurrency(baseCurrency)
                .exchangeRate(exchangeRate)
                .date(LocalDateTime.now())
                .build();
    }
}

