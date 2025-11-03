package br.com.facilit.IT;

import br.com.facilit.mapper.ResponsavelMapper;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ResponsavelResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ResponsavelControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResponsavelMapper mapper;

    private ResponsavelResponse inserirResponsavel(String nome, String email) throws Exception {
        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome(nome).email(email).build();
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        String body = mockMvc
                .perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponsavelResponse responsavelResponse = objectMapper.readValue(body, ResponsavelResponse.class);
        return responsavelResponse;
    }

    @Test
    public void consultarTodosVazioTeste() throws Exception {
        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void consultarTodosTeste() throws Exception {
        inserirResponsavel("Bento", "bento@empresa.com");
        inserirResponsavel("Mariah", "mariah@empresa.com");

        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Bento")))
                .andExpect(jsonPath("$[0].email", is("bento@empresa.com")))
                .andExpect(jsonPath("$[1].nome", is("Mariah")))
                .andExpect(jsonPath("$[1].email", is("mariah@empresa.com")));
    }

    @Test
    public void buscarTeste() throws Exception {
        ResponsavelResponse responsavelResponse = inserirResponsavel("Bento", "bento@empresa.com");
        Long id = responsavelResponse.getId();

        MvcResult mvcResult = mockMvc.perform(get("/responsaveis/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponsavelResponse result = objectMapper.readValue(body, ResponsavelResponse.class);

        assertEquals(id, result.getId());
        assertEquals("Bento", result.getNome());
        assertEquals("bento@empresa.com", result.getEmail());
    }

    @Test
    public void buscarComNotFoundTeste() throws Exception {
        mockMvc.perform(get("/responsaveis/{id}", 0)).andExpect(status().isNotFound());
    }

    @Test
    public void atualizarTeste() throws Exception {
        ResponsavelResponse responsavelResponse = inserirResponsavel("Ana", "ana@empresa.com");
        Long id = responsavelResponse.getId();

        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome("Ana Paula").email("ana.paula@empresa.com").build();

        String payload = objectMapper.writeValueAsString(responsavelRequest);

        MvcResult mvcResult = mockMvc.perform(put("/responsaveis/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponsavelResponse result = objectMapper.readValue(body, ResponsavelResponse.class);

        assertEquals(id, result.getId());
        assertEquals("Ana Paula", result.getNome());
        assertEquals("ana.paula@empresa.com", result.getEmail());
    }

    @Test
    public void atualizarCom404Teste() throws Exception {
        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome("Bento").email("bento@empresa.com").build();

        String payload = objectMapper.writeValueAsString(responsavelRequest);

        mockMvc.perform(put("/responsaveis/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletarTeste() throws Exception {
        ResponsavelResponse responsavelResponse = inserirResponsavel("Bento", "bento@empresa.com");
        Long id = responsavelResponse.getId();

        mockMvc.perform(delete("/responsaveis/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void deletarCom404Teste() throws Exception {
        mockMvc.perform(delete("/responsaveis/{id}", 0))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void inserirTeste() throws Exception {
        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome("Bento").email("bento@empresa.com").build();
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        mockMvc
                .perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Bento")))
                .andExpect(jsonPath("$.email", is("bento@empresa.com")));
    }
}
