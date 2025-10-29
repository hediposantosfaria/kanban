package br.com.facilit.config;

import br.com.facilit.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionConstantes {
    public static String CODIGO_ERRO_NEGOCIAL = "ERRO_NEGOCIAL";
    public static String CODIGO_ERRO_INESPERADO = "ERRO_INESPERADO";
    public static String MENSAGEM_ERRO_INESPERADO = "Erro inesperado.";
}




