package br.com.facilit.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponsavelResponseTest {

    @Test
    public void gettersAndSettersTest() {
        ResponsavelResponse resp = new ResponsavelResponse();

        resp.setId(1L);
        resp.setNome("Alice");
        resp.setEmail("alice@exemplo.com");

        assertEquals(Long.valueOf(1L), resp.getId());
        assertEquals("Alice", resp.getNome());
        assertEquals("alice@exemplo.com", resp.getEmail());
    }

    @Test
    public void allArgsConstructorTest() {
        ResponsavelResponse resp = new ResponsavelResponse(2L, "Bob", "bob@exemplo.com");

        assertEquals(Long.valueOf(2L), resp.getId());
        assertEquals("Bob", resp.getNome());
        assertEquals("bob@exemplo.com", resp.getEmail());
    }

    @Test
    public void builderTest() {
        ResponsavelResponse resp = ResponsavelResponse.builder()
                .id(3L)
                .nome("Carol")
                .email("carol@exemplo.com")
                .build();

        assertEquals(Long.valueOf(3L), resp.getId());
        assertEquals("Carol", resp.getNome());
        assertEquals("carol@exemplo.com", resp.getEmail());

        resp.setNome("Carol Atualizada");
        resp.setEmail("carol.atualizada@exemplo.com");
        assertEquals("Carol Atualizada", resp.getNome());
        assertEquals("carol.atualizada@exemplo.com", resp.getEmail());
    }
}
