package tech.xavi.exchangeapi.dto.rest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiExceptionDto {

    private String errorString;
    private int errorCode;
    private String message;
    private LocalDateTime moment;

}
