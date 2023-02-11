package tech.xavi.exchangeapi.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tech.xavi.exchangeapi.model.ExchangeError;

@AllArgsConstructor
@Getter
public class ExchangeApiException extends RuntimeException{

    private final ExchangeError exchangeApiError;
    @JsonIgnore
    private final HttpStatus httpStatus;


}
