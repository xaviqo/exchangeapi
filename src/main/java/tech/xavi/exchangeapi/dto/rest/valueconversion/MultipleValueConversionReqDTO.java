package tech.xavi.exchangeapi.dto.rest.valueconversion;

import lombok.Data;

import java.util.List;

@Data
public class MultipleValueConversionReqDTO {
    private String from;
    private Double amountTo;
    private List<String> targetCurrencies;
}
