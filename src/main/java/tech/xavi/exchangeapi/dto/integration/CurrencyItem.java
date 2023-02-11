package tech.xavi.exchangeapi.dto.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CurrencyItem {

    private String code;
    private String description;
}
