package br.com.facilit.IT;

import br.com.facilit.mapper.ProjetoMapper;
import br.com.facilit.request.ProjetoRequest;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ProjetoResponse;
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
public class ProjetoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProjetoMapper mapper;

    private ProjetoResponse inserirProjeto(String nomeProjeto, String nomeResponsavel, String emailResponsavel) throws Exception {
        ResponsavelResponse responsavelResponse = inserirResponsavel(nomeResponsavel, emailResponsavel);
        ProjetoRequest projetoRequest = ProjetoRequest.builder().nome(nomeProjeto).status("S").responsavelId(responsavelResponse.getId()).build();
        String payload = objectMapper.writeValueAsString(projetoRequest);
        String body = mockMvc
                .perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ProjetoResponse projetoResponse = objectMapper.readValue(body, ProjetoResponse.class);
        return projetoResponse;
    }

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
        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void consultarTodosTeste() throws Exception {
        inserirProjeto("XPTO", "Bento", "bento@empresa.com");
        inserirProjeto("Nova", "Mariah", "mariah@empresa.com");

        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("XPTO")))
                .andExpect(jsonPath("$[1].nome", is("Nova")));
    }

    @Test
    public void buscarTeste() throws Exception {
        ProjetoResponse projetoResponse = inserirProjeto("XPTO", "Bento", "bento@empresa.com");
        Long id = projetoResponse.getId();

        MvcResult mvcResult = mockMvc.perform(get("/projetos/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ProjetoResponse result = objectMapper.readValue(body, ProjetoResponse.class);

        assertEquals(id, result.getId());
        assertEquals("XPTO", result.getNome());
    }

    @Test
    public void buscarComNotFoundTeste() throws Exception {
        mockMvc.perform(get("/projetos/{id}", 0)).andExpect(status().isNotFound());
    }

    @Test
    public void atualizarTeste() throws Exception {
        ProjetoResponse projetoResponse = inserirProjeto("Nova", "Mariah", "mariah@empresa.com");
        Long id = projetoResponse.getId();
        Long responsavelId = projetoResponse.getResponsavelId();

        ProjetoRequest projetoRequest = ProjetoRequest.builder().nome("XPTO").status("A").responsavelId(responsavelId).build();

        String payload = objectMapper.writeValueAsString(projetoRequest);

        mockMvc.perform(put("/projetos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("XPTO")));
    }

    @Test
    public void atualizarCom404Teste() throws Exception {
        ProjetoRequest projetoRequest = ProjetoRequest.builder().nome("XPTO").build();

        String payload = objectMapper.writeValueAsString(projetoRequest);

        mockMvc.perform(put("/projetos/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletarTeste() throws Exception {
        ProjetoResponse projetoResponse = inserirProjeto("XPTO", "Bento", "bento@empresa.com");
        Long id = projetoResponse.getId();

        mockMvc.perform(delete("/projetos/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void deletarCom404Teste() throws Exception {
        mockMvc.perform(delete("/projetos/{id}", 0))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void inserirTeste() throws Exception {
        ResponsavelResponse responsavelResponse = inserirResponsavel("Bento", "bento@empresa.com");
        ProjetoRequest projetoRequest = ProjetoRequest.builder().nome("XPTO").status("S").responsavelId(responsavelResponse.getId()).build();
        String payload = objectMapper.writeValueAsString(projetoRequest);
        mockMvc
                .perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("XPTO")));
    }

}
