package br.com.facilit.exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NegocioExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void construtorRuntimeExceptionTest() {
        String mensagem = "Falha de negócio.";
        NegocioException ex = new NegocioException(mensagem);

        assertTrue(ex instanceof RuntimeException);
        assertEquals(mensagem, ex.getMessage());
    }

    @Test
    public void thrownMensagemEsperadaTest() {
        thrown.expect(NegocioException.class);
        thrown.expectMessage("Erro específico.");

        throw new NegocioException("Erro específico.");
    }
}
