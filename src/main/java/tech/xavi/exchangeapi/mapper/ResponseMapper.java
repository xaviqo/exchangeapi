package tech.xavi.exchangeapi.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponse;
import tech.xavi.exchangeapi.dto.integration.LatestRatesIntegrationResponse;

import java.util.Map;

@Component
@AllArgsConstructor
public class ResponseMapper {

    private final ModelMapper modelMapper;

    public LatestRatesIntegrationResponse toLatestRates(Map<String,Object> mapResponse){
        return modelMapper.map(mapResponse, LatestRatesIntegrationResponse.class);
    }

    public AvailableSymbolsResponse toAvailableSymbols(Map<String,Object> mapResponse){
        return modelMapper.map(mapResponse, AvailableSymbolsResponse.class);
    }

}
