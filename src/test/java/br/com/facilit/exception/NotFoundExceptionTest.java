package br.com.facilit.exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotFoundExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void construtorRuntimeExceptionTest() {
        String mensagem = "Recurso não encontrado.";
        NotFoundException ex = new NotFoundException(mensagem);

        assertTrue(ex instanceof RuntimeException);
        assertEquals(mensagem, ex.getMessage());
    }

    @Test
    public void thrownComMensagemEsperadaTest() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Item ausente.");

        throw new NotFoundException("Item ausente.");
    }


}
