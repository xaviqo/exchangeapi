package tech.xavi.exchangeapi.dto.rest.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiExceptionDTO {

    private String errorString;
    private int errorCode;
    private String message;
    private LocalDateTime moment;

}
