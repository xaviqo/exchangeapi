package tech.xavi.exchangeapi.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;
import tech.xavi.exchangeapi.dto.integration.ExternalExchangeApiResponse;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
@AllArgsConstructor
@Slf4j
public class IntegrationCall implements ExchangeApiConstants {

    private final WebClient webClient;
    public ExternalExchangeApiResponse requestCurrentRates(){

        // TODO: Configure Webclient to only log when debug mode is enabled

        return webClient.get()
                .retrieve()
                .bodyToMono(ExternalExchangeApiResponse.class)
                .timeout(Duration.ofSeconds(EXTERNAL_API_TIMEOUT))
                .doOnError(TimeoutException.class, timeout -> {
                    log.error("External Exchange API timout exceeded");
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .log()
                .block();

    }
}
