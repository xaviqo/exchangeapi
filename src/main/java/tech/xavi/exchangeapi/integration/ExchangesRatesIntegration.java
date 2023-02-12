package tech.xavi.exchangeapi.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponse;
import tech.xavi.exchangeapi.dto.integration.LatestRatesIntegrationResponse;
import tech.xavi.exchangeapi.model.ExchangeError;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangesRatesIntegration {

    @Value("${exchange-rate.host.end-point.rates}")
    private String ratesUrl;
    @Value("${exchange-rate.host.end-point.symbols}")
    private String symbolsUrl;
    @Value("${exchange-rate.host.time-out-sec}")
    private int timeout;
    private final WebClient webClient;

    public LatestRatesIntegrationResponse getAllRates(){

        log.debug("An external API call for path '"+ratesUrl+"' has been made");

        return webClient.get()
                .uri(ratesUrl)
                .retrieve()
                .bodyToMono(LatestRatesIntegrationResponse.class)
                .timeout(Duration.ofSeconds(timeout))
                .doOnError(TimeoutException.class, timeout -> {
                    throw new ExchangeApiException(ExchangeError.INTEGRATION_TIMEOUT, HttpStatus.REQUEST_TIMEOUT);
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .block();


    }

    public AvailableSymbolsResponse getAllSymbols(){

        log.debug("An external API call for path '"+symbolsUrl+"' has been made");

        return webClient.get()
                .uri(symbolsUrl)
                .retrieve()
                .bodyToMono(AvailableSymbolsResponse.class)
                .timeout(Duration.ofSeconds(timeout))
                .doOnError(TimeoutException.class, timeout -> {
                    throw new ExchangeApiException(ExchangeError.INTEGRATION_TIMEOUT, HttpStatus.REQUEST_TIMEOUT);
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .block();


    }
}
