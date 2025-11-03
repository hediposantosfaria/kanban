package br.com.facilit.request;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponsavelRequestTest {

    @Test
    public void gettersAndSettersTest() {
        ResponsavelRequest req = new ResponsavelRequest();

        req.setNome("Alice");
        req.setEmail("alice@empresa.com");

        assertEquals("Alice", req.getNome());
        assertEquals("alice@empresa.com", req.getEmail());
    }

    @Test
    public void allArgsConstructorTest() {
        ResponsavelRequest req = new ResponsavelRequest("Bob", "bob@empresa.com");

        assertEquals("Bob", req.getNome());
        assertEquals("bob@empresa.com", req.getEmail());
    }

    @Test
    public void builderTest() {
        ResponsavelRequest req = ResponsavelRequest.builder()
                .nome("Carol")
                .email("carol@empresa.com")
                .build();

        assertEquals("Carol", req.getNome());
        assertEquals("carol@empresa.com", req.getEmail());

        req.setNome("Carol Atualizada");
        req.setEmail("carol.atualizada@empresa.com");
        assertEquals("Carol Atualizada", req.getNome());
        assertEquals("carol.atualizada@empresa.com", req.getEmail());
    }

}
