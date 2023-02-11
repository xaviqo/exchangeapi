package tech.xavi.exchangeapi.dto.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class LatestRatesIntegrationResponse {

    private String base;
    private boolean success;
    private String date;
    private Map<String,Double> rates;

}
