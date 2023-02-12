package tech.xavi.exchangeapi.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.integration.AvailableSymbolsResponseDTO;
import tech.xavi.exchangeapi.dto.integration.LatestRatesIntegrationResponseDTO;
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
    @Value("${api.cfg.base-currency}")
    private String baseCurrency;
    private final WebClient webClient;

    public LatestRatesIntegrationResponseDTO getAllRates(){

        log.debug("An external API call for path '"+ratesUrl+"' has been made");

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(ratesUrl)
                                .queryParam("base",baseCurrency)
                                .build())
                .retrieve()
                .bodyToMono(LatestRatesIntegrationResponseDTO.class)
                .timeout(Duration.ofSeconds(timeout))
                .doOnError(TimeoutException.class, timeout -> {
                    throw new ExchangeApiException(ExchangeError.INTEGRATION_TIMEOUT, HttpStatus.REQUEST_TIMEOUT);
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .block();

    }

    public AvailableSymbolsResponseDTO getAllSymbols(){

        log.debug("An external API call for path '"+symbolsUrl+"' has been made");

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(symbolsUrl)
                                .queryParam("base",baseCurrency)
                                .build())
                .retrieve()
                .bodyToMono(AvailableSymbolsResponseDTO.class)
                .timeout(Duration.ofSeconds(timeout))
                .doOnError(TimeoutException.class, timeout -> {
                    throw new ExchangeApiException(ExchangeError.INTEGRATION_TIMEOUT, HttpStatus.REQUEST_TIMEOUT);
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .block();

    }
}
