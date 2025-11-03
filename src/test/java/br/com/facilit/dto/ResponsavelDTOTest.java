package br.com.facilit.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponsavelDTOTest {

    @Test
    public void gettersAndSettersTest() {
        ResponsavelDTO dto = new ResponsavelDTO();
        dto.setId(1L);
        dto.setNome("Alice");
        dto.setEmail("alice@empresa.com");

        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("Alice", dto.getNome());
        assertEquals("alice@empresa.com", dto.getEmail());
    }

    @Test
    public void allArgsConstructorTest() {
        ResponsavelDTO dto = new ResponsavelDTO(2L, "Bob", "bob@empresa.com");

        assertEquals(Long.valueOf(2L), dto.getId());
        assertEquals("Bob", dto.getNome());
        assertEquals("bob@empresa.com", dto.getEmail());
    }

    @Test
    public void builderTest() {
        ResponsavelDTO dto = ResponsavelDTO.builder()
                .id(3L)
                .nome("Carol")
                .email("carol@empresa.com")
                .build();

        assertEquals(Long.valueOf(3L), dto.getId());
        assertEquals("Carol", dto.getNome());
        assertEquals("carol@empresa.com", dto.getEmail());

        dto.setNome("Carol Atualizada");
        assertEquals("Carol Atualizada", dto.getNome());
    }

    @Test
    public void equalsHashCodeTest() {
        ResponsavelDTO a = new ResponsavelDTO(10L, "Nome", "email@empresa.com");
        ResponsavelDTO b = new ResponsavelDTO(10L, "Nome", "email@empresa.com");
        ResponsavelDTO c = ResponsavelDTO.builder().id(10L).nome("Nome").email("email@empresa.com").build();

        assertTrue(a.equals(a));

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        assertTrue(b.equals(c));
        assertTrue(a.equals(c));

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(b.hashCode(), c.hashCode());

        assertFalse(a.equals(new ResponsavelDTO(11L, "Nome", "email@empresa.com")));
        assertFalse(a.equals(new ResponsavelDTO(10L, "Outro", "email@empresa.com")));
        assertFalse(a.equals(new ResponsavelDTO(10L, "Nome", "outro@empresa.com")));

        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));
    }

    @Test
    public void equalsHashCodeNullTest() {
        ResponsavelDTO n1 = new ResponsavelDTO(null, null, null);
        ResponsavelDTO n2 = new ResponsavelDTO(null, null, null);

        assertTrue(n1.equals(n2));
        assertEquals(n1.hashCode(), n2.hashCode());

        n2.setEmail("any@empresa.com");
        assertFalse(n1.equals(n2));
    }
}
