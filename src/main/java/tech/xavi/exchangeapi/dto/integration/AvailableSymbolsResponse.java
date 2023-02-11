package tech.xavi.exchangeapi.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSymbolsResponse {

    private Map<String,CurrencyItem> symbols;
}
