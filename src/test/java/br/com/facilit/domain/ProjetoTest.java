package br.com.facilit.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjetoTest {

    @Test
    public void gettersAndSettersTest() {
        Projeto p = new Projeto();
        Long id = 10L;
        String nome = "Projeto X";
        String status = "EM_ANDAMENTO";
        LocalDate inicioPrevisto = LocalDate.of(2025, 1, 1);
        LocalDate terminoPrevisto = LocalDate.of(2025, 12, 31);
        LocalDate inicioRealizado = LocalDate.of(2025, 2, 1);
        LocalDate terminoRealizado = LocalDate.of(2025, 11, 30);

        p.setId(id);
        p.setNome(nome);
        p.setStatus(status);
        p.setInicioPrevisto(inicioPrevisto);
        p.setTerminoPrevisto(terminoPrevisto);
        p.setInicioRealizado(inicioRealizado);
        p.setTerminoRealizado(terminoRealizado);
        p.setResponsavel(null);

        assertEquals(id, p.getId());
        assertEquals(nome, p.getNome());
        assertEquals(status, p.getStatus());
        assertEquals(inicioPrevisto, p.getInicioPrevisto());
        assertEquals(terminoPrevisto, p.getTerminoPrevisto());
        assertEquals(inicioRealizado, p.getInicioRealizado());
        assertEquals(terminoRealizado, p.getTerminoRealizado());
        assertNull(p.getResponsavel());
    }

    @Test
    public void allArgsConstructorTest() {
        Long id = 1L;
        String nome = "P";
        String status = "N";
        LocalDate inicioPrevisto = LocalDate.of(2024, 1, 10);
        LocalDate terminoPrevisto = LocalDate.of(2024, 2, 20);
        LocalDate inicioRealizado = LocalDate.of(2024, 1, 15);
        LocalDate terminoRealizado = LocalDate.of(2024, 2, 18);

        Projeto p = new Projeto(
                id,
                nome,
                status,
                inicioPrevisto,
                terminoPrevisto,
                inicioRealizado,
                terminoRealizado,
                null
        );

        assertEquals(id, p.getId());
        assertEquals(nome, p.getNome());
        assertEquals(status, p.getStatus());
        assertEquals(inicioPrevisto, p.getInicioPrevisto());
        assertEquals(terminoPrevisto, p.getTerminoPrevisto());
        assertEquals(inicioRealizado, p.getInicioRealizado());
        assertEquals(terminoRealizado, p.getTerminoRealizado());
        assertNull(p.getResponsavel());
    }

}
