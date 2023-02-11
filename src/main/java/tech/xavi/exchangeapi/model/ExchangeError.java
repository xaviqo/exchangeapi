package tech.xavi.exchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExchangeError {

    BAD_REQUEST("Incorrect or non-processable data",400),
    METHOD_NOT_ALLOWED("Unauthorized HTTP method",405),
    INTERNAL_SERVER_ERROR("Internal API Error",500),
    INVALID_CURRENCY("Invalid Currency",452)
    ;

    private final String message;
    private final int errorCode;
}
