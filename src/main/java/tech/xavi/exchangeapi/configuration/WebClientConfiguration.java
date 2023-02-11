package tech.xavi.exchangeapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

@Configuration
public class WebClientConfiguration implements ExchangeApiConstants {

    @Bean
    public WebClient webclient() {

        return WebClient
                .builder()
                .baseUrl(EXTERNAL_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }
}
