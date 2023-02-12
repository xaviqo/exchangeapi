package tech.xavi.exchangeapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class CurrencyOperationServiceTest {

    @Autowired
    private CurrencyOperationService operationService;

    @Test
    void shouldConvertMultipleValuesCorrectly() {
        // GIVEN
        Map<String,Double> rates = new HashMap<>();
        rates.put("USD",1.06);
        rates.put("STD",24379.38);
        rates.put("MXN",19.934);
        rates.put("EUR",1.0);

        Map<String,Double> expectedResults = new HashMap<>();
        expectedResults.put("USD", 106.0);
        expectedResults.put("STD", 2437938.0);
        expectedResults.put("MXN", 1993.4);
        expectedResults.put("EUR", 100.0);

        // WHEN
        Map<String,Double> result = operationService.multipleValueConversion(100.0,rates);

        // THEN
        assertEquals(expectedResults,result);
    }

    @Test
    void shouldReturnCorrectConvertedRates() {
        // GIVEN
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 1.00);
        rates.put("XYZ", 55.55);
        rates.put("ABC", 20.23);
        rates.put("LOL", 13.37);
        rates.put("TST", 10.0);

        Map<String, Double> expectedResults = new HashMap<>();
        expectedResults.put("EUR", 0.1);
        expectedResults.put("XYZ", 5.555);
        expectedResults.put("ABC", 2.023);
        expectedResults.put("LOL", 1.337);
        expectedResults.put("TST", 1.0);

        // WHEN
        Map<String,Double> result = operationService.allRatesFromCurrency("TST",rates);

        // THEN
        assertEquals(expectedResults,result);
    }

}