package br.com.facilit.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponsavelTest {

    @Test
    public void gettersAndSettersTest() {
        Responsavel r = new Responsavel();
        Long id = 42L;
        String nome = "Alice";
        String email = "alice@empresa.com";

        r.setId(id);
        r.setNome(nome);
        r.setEmail(email);

        assertEquals(id, r.getId());
        assertEquals(nome, r.getNome());
        assertEquals(email, r.getEmail());
    }

    @Test
    public void allArgsConstructorTest() {
        Long id = 1L;
        String nome = "Bob";
        String email = "bob@empresa.com";

        Responsavel r = new Responsavel(id, nome, email);

        assertEquals(id, r.getId());
        assertEquals(nome, r.getNome());
        assertEquals(email, r.getEmail());
    }

    @Test
    public void builderTest() {
        Responsavel r = Responsavel.builder()
                .id(7L)
                .nome("Carol")
                .email("carol@empresa.com")
                .build();

        assertEquals(Long.valueOf(7L), r.getId());
        assertEquals("Carol", r.getNome());
        assertEquals("carol@empresa.com", r.getEmail());

        r.setNome("Carol Atualizada");
        assertEquals("Carol Atualizada", r.getNome());
    }

}
