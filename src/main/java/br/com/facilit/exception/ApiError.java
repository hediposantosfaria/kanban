package br.com.facilit.exception;

import java.time.OffsetDateTime;

public class ApiError {
    public String code;
    public String message;
    public OffsetDateTime timestamp = OffsetDateTime.now();

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
