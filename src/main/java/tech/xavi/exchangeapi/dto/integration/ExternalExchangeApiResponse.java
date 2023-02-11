package tech.xavi.exchangeapi.dto.integration;

import lombok.Data;

import java.util.Map;

@Data
public class ExternalExchangeApiResponse {

    private String base;
    private boolean success;
    private String date;
    private Map<String,Double> rates;

}
