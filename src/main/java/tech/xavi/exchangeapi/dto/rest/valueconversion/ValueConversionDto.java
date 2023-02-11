package tech.xavi.exchangeapi.dto.rest.valueconversion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ValueConversionDto {

    private String from;
    private Double amountFrom;
    private String to;
    private Double amountTo;
    private String baseCurrency;
    private Double exchangeRate;
    private LocalDateTime date;

}
