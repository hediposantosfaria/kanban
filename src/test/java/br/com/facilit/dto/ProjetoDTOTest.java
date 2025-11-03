package br.com.facilit.dto;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjetoDTOTest {

    @Test
    public void gettersAndSettersTest() {
        ProjetoDTO dto = new ProjetoDTO();

        Long id = 100L;
        String nome = "Projeto Alpha";
        String status = "N";
        LocalDate inicioPrevisto = LocalDate.of(2025, 1, 10);
        LocalDate terminoPrevisto = LocalDate.of(2025, 12, 20);
        LocalDate inicioRealizado = LocalDate.of(2025, 2, 1);
        LocalDate terminoRealizado = LocalDate.of(2025, 11, 30);

        dto.setId(id);
        dto.setNome(nome);
        dto.setStatus(status);
        dto.setInicioPrevisto(inicioPrevisto);
        dto.setTerminoPrevisto(terminoPrevisto);
        dto.setInicioRealizado(inicioRealizado);
        dto.setTerminoRealizado(terminoRealizado);
        dto.setResponsavel(null);

        assertEquals(id, dto.getId());
        assertEquals(nome, dto.getNome());
        assertEquals(status, dto.getStatus());
        assertEquals(inicioPrevisto, dto.getInicioPrevisto());
        assertEquals(terminoPrevisto, dto.getTerminoPrevisto());
        assertEquals(inicioRealizado, dto.getInicioRealizado());
        assertEquals(terminoRealizado, dto.getTerminoRealizado());
        assertNull(dto.getResponsavel());
    }

    @Test
    public void allArgsConstructorTest() {
        Long id = 1L;
        String nome = "Projeto Beta";
        String status = "N";
        LocalDate inicioPrevisto = LocalDate.of(2024, 3, 1);
        LocalDate terminoPrevisto = LocalDate.of(2024, 9, 1);
        LocalDate inicioRealizado = LocalDate.of(2024, 3, 5);
        LocalDate terminoRealizado = LocalDate.of(2024, 8, 30);

        ProjetoDTO dto = new ProjetoDTO(
                id,
                nome,
                status,
                inicioPrevisto,
                terminoPrevisto,
                inicioRealizado,
                terminoRealizado,
                null
        );

        assertEquals(id, dto.getId());
        assertEquals(nome, dto.getNome());
        assertEquals(status, dto.getStatus());
        assertEquals(inicioPrevisto, dto.getInicioPrevisto());
        assertEquals(terminoPrevisto, dto.getTerminoPrevisto());
        assertEquals(inicioRealizado, dto.getInicioRealizado());
        assertEquals(terminoRealizado, dto.getTerminoRealizado());
        assertNull(dto.getResponsavel());
    }

    @Test
    public void builderTest() {
        ProjetoDTO dto = ProjetoDTO.builder()
                .id(7L)
                .nome("Projeto Gama")
                .status("P")
                .inicioPrevisto(LocalDate.of(2026, 1, 1))
                .terminoPrevisto(LocalDate.of(2026, 6, 30))
                .inicioRealizado(null)
                .terminoRealizado(null)
                .responsavel(null)
                .build();

        assertEquals(Long.valueOf(7L), dto.getId());
        assertEquals("Projeto Gama", dto.getNome());
        assertEquals("P", dto.getStatus());
        assertEquals(LocalDate.of(2026, 1, 1), dto.getInicioPrevisto());
        assertEquals(LocalDate.of(2026, 6, 30), dto.getTerminoPrevisto());
        assertNull(dto.getInicioRealizado());
        assertNull(dto.getTerminoRealizado());
        assertNull(dto.getResponsavel());

        dto.setNome("Projeto Gama Atualizado");
        dto.setStatus("A");
        assertEquals("Projeto Gama Atualizado", dto.getNome());
        assertEquals("A", dto.getStatus());
    }
}
