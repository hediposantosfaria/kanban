package br.com.facilit.response;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjetoResponseTest {

    @Test
    public void gettersAndSettersTest() {
        ProjetoResponse resp = new ProjetoResponse();

        Long id = 100L;
        String nome = "Projeto X";
        String status = "EM_ANDAMENTO";
        LocalDate inicioPrevisto = LocalDate.of(2025, 1, 10);
        LocalDate terminoPrevisto = LocalDate.of(2025, 12, 20);
        LocalDate inicioRealizado = LocalDate.of(2025, 2, 1);
        LocalDate terminoRealizado = LocalDate.of(2025, 11, 30);
        Long responsavelId = 99L;

        resp.setId(id);
        resp.setNome(nome);
        resp.setStatus(status);
        resp.setInicioPrevisto(inicioPrevisto);
        resp.setTerminoPrevisto(terminoPrevisto);
        resp.setInicioRealizado(inicioRealizado);
        resp.setTerminoRealizado(terminoRealizado);
        resp.setResponsavelId(responsavelId);

        assertEquals(id, resp.getId());
        assertEquals(nome, resp.getNome());
        assertEquals(status, resp.getStatus());
        assertEquals(inicioPrevisto, resp.getInicioPrevisto());
        assertEquals(terminoPrevisto, resp.getTerminoPrevisto());
        assertEquals(inicioRealizado, resp.getInicioRealizado());
        assertEquals(terminoRealizado, resp.getTerminoRealizado());
        assertEquals(responsavelId, resp.getResponsavelId());
    }

    @Test
    public void allArgsConstructorTest() {
        ProjetoResponse resp = new ProjetoResponse(
                1L,
                "Projeto Y",
                "NOVO",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 8, 30),
                7L
        );

        assertEquals(Long.valueOf(1L), resp.getId());
        assertEquals("Projeto Y", resp.getNome());
        assertEquals("NOVO", resp.getStatus());
        assertEquals(LocalDate.of(2024, 3, 1), resp.getInicioPrevisto());
        assertEquals(LocalDate.of(2024, 9, 1), resp.getTerminoPrevisto());
        assertEquals(LocalDate.of(2024, 4, 1), resp.getInicioRealizado());
        assertEquals(LocalDate.of(2024, 8, 30), resp.getTerminoRealizado());
        assertEquals(Long.valueOf(7L), resp.getResponsavelId());
    }

    @Test
    public void builderTest() {
        ProjetoResponse resp = ProjetoResponse.builder()
                .id(5L)
                .nome("Projeto Z")
                .status("PLANEJADO")
                .inicioPrevisto(LocalDate.of(2026, 1, 1))
                .terminoPrevisto(LocalDate.of(2026, 6, 30))
                .inicioRealizado(null)
                .terminoRealizado(null)
                .responsavelId(55L)
                .build();

        assertEquals(Long.valueOf(5L), resp.getId());
        assertEquals("Projeto Z", resp.getNome());
        assertEquals("PLANEJADO", resp.getStatus());
        assertEquals(LocalDate.of(2026, 1, 1), resp.getInicioPrevisto());
        assertEquals(LocalDate.of(2026, 6, 30), resp.getTerminoPrevisto());
        assertNull(resp.getInicioRealizado());
        assertNull(resp.getTerminoRealizado());
        assertEquals(Long.valueOf(55L), resp.getResponsavelId());

        resp.setStatus("EM_ANDAMENTO");
        resp.setNome("Projeto Z Atualizado");
        assertEquals("EM_ANDAMENTO", resp.getStatus());
        assertEquals("Projeto Z Atualizado", resp.getNome());
    }
}
