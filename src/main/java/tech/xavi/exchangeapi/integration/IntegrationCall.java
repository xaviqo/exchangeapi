package tech.xavi.exchangeapi.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
@AllArgsConstructor
@Slf4j
public class IntegrationCall implements ExchangeApiConstants {

    private final WebClient webClient;

    public Map<String, Object> callEndpoint(String path){

        // TODO: Configure Webclient to only log when debug mode is enabled

        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(EXTERNAL_API_TIMEOUT))
                .doOnError(TimeoutException.class, timeout -> {
                    log.error("External Exchange API timout exceeded");
                })
                .onErrorResume(TimeoutException.class, e -> Mono.empty())
                .log()
                .block();

    }
}
