package br.com.facilit.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExceptionConstantesTest {

    @Test
    public void deveConterOsValoresEsperadosTest() {
        assertEquals("ERRO_NEGOCIAL", ExceptionConstantes.CODIGO_ERRO_NEGOCIAL);
        assertEquals("ERRO_INESPERADO", ExceptionConstantes.CODIGO_ERRO_INESPERADO);
        assertEquals("Erro inesperado.", ExceptionConstantes.MENSAGEM_ERRO_INESPERADO);
    }

    @Test
    public void devePermitirInstanciacaoPadraoTest() {
        ExceptionConstantes instance = new ExceptionConstantes();
        assertNotNull(instance);
    }
}
