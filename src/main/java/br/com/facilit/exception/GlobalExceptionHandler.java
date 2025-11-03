package br.com.facilit.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiError> handleBusiness(NegocioException ex) {
        return ResponseEntity.status(422).body(new ApiError("BUSINESS_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(Exception ex) {
        return ResponseEntity.status(409).body(new ApiError("CONSTRAINT_VIOLATION", "Violação de integridade de dados."));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleRse(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ApiError(ex.getStatus().name(), ex.getReason()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ResponseStatus rs = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (rs != null) {
            HttpStatus status = rs.code() != HttpStatus.INTERNAL_SERVER_ERROR ? rs.code() : rs.value();
            return ResponseEntity.status(status).body(new ApiError(status.name(), ex.getMessage()));
        }
        return ResponseEntity.status(500).body(new ApiError("ERRO_INESPERADO", "Erro inesperado."));
    }
}
