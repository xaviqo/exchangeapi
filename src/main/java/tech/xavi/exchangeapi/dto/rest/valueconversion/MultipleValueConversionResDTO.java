package tech.xavi.exchangeapi.dto.rest.valueconversion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class MultipleValueConversionResDTO {

    private String from;
    private Double amountTo;
    private String baseExchangeCurrency;
    private Map<String,Double> targetConversions;
    private LocalDateTime date;

}
