package br.com.facilit.exception;

import org.junit.Test;

import java.time.Duration;
import java.time.OffsetDateTime;

import static org.junit.Assert.*;

public class ApiErrorTest {

    @Test
    public void construtorTimestampTest() {
        OffsetDateTime antes = OffsetDateTime.now();

        ApiError error = new ApiError("COD", "mensagem qualquer");

        OffsetDateTime depois = OffsetDateTime.now();

        assertEquals("COD", error.code);
        assertEquals("mensagem qualquer", error.message);
        assertNotNull(error.timestamp);

        assertFalse(error.timestamp.isBefore(antes));
        assertFalse(error.timestamp.isAfter(depois));

        long segundos = Duration.between(antes, error.timestamp).getSeconds();
        assertTrue(Math.abs(segundos) < 5);
    }

    @Test
    public void camposPublicosTest() {
        ApiError error = new ApiError("X", "Y");
        error.code = "NOVO CODIGO";
        error.message = "NOVA MENSAGEM";
        error.timestamp = OffsetDateTime.now().minusDays(1);

        assertEquals("NOVO CODIGO", error.code);
        assertEquals("NOVA MENSAGEM", error.message);
        assertNotNull(error.timestamp);
    }
}
