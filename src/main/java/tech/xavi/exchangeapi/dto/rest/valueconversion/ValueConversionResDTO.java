package tech.xavi.exchangeapi.dto.rest.valueconversion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ValueConversionResDTO {

    private String from;
    private Double amountFrom;
    private String to;
    private Double amountTo;
    private String baseExchangeCurrency;
    private Double exchangeRate;
    private LocalDateTime date;

}
