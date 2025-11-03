package br.com.facilit.cucumber;


import br.com.facilit.config.CucumberSpringConfiguration;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ResponsavelResponse;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResponsavelStepdefs extends CucumberSpringConfiguration {

    private final TestContext contexto = new TestContext();

    @Dado("que existe o seguinte responsável cadastrado")
    public void queExisteUmResponsavel(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        String email = row.get("Email");
        String payload = objectMapper.writeValueAsString(ResponsavelRequest.builder().nome(nome).email(email).build());
        MvcResult mvcResult = mockMvc.perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponsavelResponse criado = objectMapper.readValue(contexto.ultimoBody, ResponsavelResponse.class);
        contexto.ultimoIdSalvo = criado.getId();
    }

    @Quando("é realiza uma requisição para consultar todos os responsáveis")
    public void consultarTodos() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/responsaveis")).andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para consultar um responsável")
    public void consultarUm() throws Exception {
        long id = (contexto.ultimoIdSalvo != null) ? contexto.ultimoIdSalvo : 0L;
        MvcResult mvcResult = mockMvc.perform(get("/responsaveis/{id}", id)).andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para altererar um responsável")
    public void alterarUm(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        String email = row.get("Email");
        long id = (contexto.ultimoIdSalvo != null) ? contexto.ultimoIdSalvo : 0L;
        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome(nome).email(email).build();
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        MvcResult mvcResult = mockMvc.perform(put("/responsaveis/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para apagar um responsável")
    public void apagarUm() throws Exception {
        long id = (contexto.ultimoIdSalvo != null) ? contexto.ultimoIdSalvo : 0L;
        MvcResult mvcResult = mockMvc.perform(delete("/responsaveis/{id}", id)).andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = "";
    }

    @Quando("é realizada uma requisição para inserir um responsável")
    public void inserirUm(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        String email = row.get("Email");
        ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome(nome).email(email).build();
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        MvcResult mvcResult = mockMvc.perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        contexto.ultimoStatus = mvcResult.getResponse().getStatus();
        contexto.ultimoBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Então("o sistema deve retornar os dados de responsáveis vazios")
    public void respostaVazia() throws Exception {
        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Então("o sistema deve retornar os seguintes dados de responsáveis")
    public void sistemaDeveRetornar(DataTable tabelaEsperada) throws Exception {
        JsonNode json = objectMapper.readTree(contexto.ultimoBody);
        List<Map<String, String>> rows = tabelaEsperada.asMaps();
        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(rows.size())));
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);
            mockMvc.perform(get("/responsaveis"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].nome", is(row.get("Nome"))))
                    .andExpect(jsonPath("$[" + i + "].email", is(row.get("Email"))));
        }
    }

    @Então("o sistema deve retornar o seguinte dado de responsável")
    public void sistemaDeveRetornarOsDadosDeResponsavel(DataTable tabelaEsperada) throws Exception {
        JsonNode json = objectMapper.readTree(contexto.ultimoBody);
        Map<String, String> row = tabelaEsperada.asMaps().get(0);
        mockMvc.perform(get("/responsaveis/{id}", json.get("id").asLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(row.get("Nome"))))
                .andExpect(jsonPath("$.email", is(row.get("Email"))));
    }

    @Então("a resposta deve ser responsável não encontrado")
    public void respostaNaoEncontrado() {
        assertEquals(404, contexto.ultimoStatus);
    }

    @Então("o sistema deve apagar o responsável com sucesso")
    public void apagarComSucesso() {
        assertEquals(204, contexto.ultimoStatus);
    }

}
