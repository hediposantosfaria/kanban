package br.com.facilit.request;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjetoRequestTest {

    @Test
    public void gettersAndSettersTest() {
        ProjetoRequest req = new ProjetoRequest();

        String nome = "Projeto A";
        String status = "N";
        LocalDate inicioPrevisto = LocalDate.of(2025, 1, 10);
        LocalDate terminoPrevisto = LocalDate.of(2025, 12, 20);
        LocalDate inicioRealizado = LocalDate.of(2025, 2, 1);
        LocalDate terminoRealizado = LocalDate.of(2025, 11, 30);
        Long responsavelId = 99L;

        req.setNome(nome);
        req.setStatus(status);
        req.setInicioPrevisto(inicioPrevisto);
        req.setTerminoPrevisto(terminoPrevisto);
        req.setInicioRealizado(inicioRealizado);
        req.setTerminoRealizado(terminoRealizado);
        req.setResponsavelId(responsavelId);

        assertEquals(nome, req.getNome());
        assertEquals(status, req.getStatus());
        assertEquals(inicioPrevisto, req.getInicioPrevisto());
        assertEquals(terminoPrevisto, req.getTerminoPrevisto());
        assertEquals(inicioRealizado, req.getInicioRealizado());
        assertEquals(terminoRealizado, req.getTerminoRealizado());
        assertEquals(responsavelId, req.getResponsavelId());
    }

    @Test
    public void allArgsConstructorTest() {
        ProjetoRequest req = new ProjetoRequest(
                "Projeto B",
                "N",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 8, 30),
                7L
        );

        assertEquals("Projeto B", req.getNome());
        assertEquals("N", req.getStatus());
        assertEquals(LocalDate.of(2024, 3, 1), req.getInicioPrevisto());
        assertEquals(LocalDate.of(2024, 9, 1), req.getTerminoPrevisto());
        assertEquals(LocalDate.of(2024, 4, 1), req.getInicioRealizado());
        assertEquals(LocalDate.of(2024, 8, 30), req.getTerminoRealizado());
        assertEquals(Long.valueOf(7L), req.getResponsavelId());
    }

    @Test
    public void builderTest() {
        ProjetoRequest req = ProjetoRequest.builder()
                .nome("Projeto C")
                .status("P")
                .inicioPrevisto(LocalDate.of(2026, 1, 1))
                .terminoPrevisto(LocalDate.of(2026, 6, 30))
                .inicioRealizado(null)
                .terminoRealizado(null)
                .responsavelId(123L)
                .build();

        assertEquals("Projeto C", req.getNome());
        assertEquals("P", req.getStatus());
        assertEquals(LocalDate.of(2026, 1, 1), req.getInicioPrevisto());
        assertEquals(LocalDate.of(2026, 6, 30), req.getTerminoPrevisto());
        assertNull(req.getInicioRealizado());
        assertNull(req.getTerminoRealizado());
        assertEquals(Long.valueOf(123L), req.getResponsavelId());

        req.setStatus("A");
        assertEquals("A", req.getStatus());
    }

}
