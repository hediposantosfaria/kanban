package br.com.facilit.cucumber;


import br.com.facilit.config.CucumberSpringConfiguration;
import br.com.facilit.request.ProjetoRequest;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ProjetoResponse;
import br.com.facilit.response.ResponsavelResponse;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjetoStepdefs extends CucumberSpringConfiguration {

    private final TestContext ctx = new TestContext();

    @Dado("que existe o seguinte projeto cadastrado")
    public void queExisteOSeguinteProjetoCadastrado(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        ResponsavelResponse responsavelResponse = inserirResponsavel(nome, nome + "@empresa.com");
        ProjetoRequest projetoRequest = ProjetoRequest
                .builder()
                .nome(nome)
                .responsavelId(responsavelResponse.getId())
                .status("S")
                .build();
        String payload = objectMapper.writeValueAsString(projetoRequest);

        MvcResult res = mockMvc.perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn();

        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ctx.ultimoIdResponsavelSalvo = responsavelResponse.getId();

        ProjetoResponse criado = objectMapper.readValue(ctx.ultimoBody, ProjetoResponse.class);
        ctx.ultimoIdSalvo = criado.getId();
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

    @Quando("é realizada uma requisição para consultar todos os projetos")
    public void consultarTodos() throws Exception {
        MvcResult res = mockMvc.perform(get("/projetos"))
                .andReturn();
        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para consultar um projeto")
    public void consultarUmProjeto() throws Exception {
        long id = (ctx.ultimoIdSalvo != null) ? ctx.ultimoIdSalvo : 0L;
        MvcResult res = mockMvc.perform(get("/projetos/{id}", id)).andReturn();
        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para altererar um projeto")
    public void alterarUmProjeto(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        long id = (ctx.ultimoIdSalvo != null) ? ctx.ultimoIdSalvo : 0L;
        ProjetoRequest projetoRequest = ProjetoRequest
                .builder()
                .nome(nome)
                .status("S")
                .responsavelId(ctx.ultimoIdResponsavelSalvo)
                .build();
        String payload = objectMapper.writeValueAsString(projetoRequest);
        MvcResult res = mockMvc.perform(put("/projetos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Quando("é realizada uma requisição para apagar um projeto")
    public void apagarUmProjeto() throws Exception {
        long id = (ctx.ultimoIdSalvo != null) ? ctx.ultimoIdSalvo : 0L;
        MvcResult res = mockMvc.perform(delete("/projetos/{id}", id))
                .andReturn();
        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = "";
    }

    @Quando("é realizada uma requisição para inserir um projeto")
    public void inserirUmProjeto(DataTable tabela) throws Exception {
        Map<String, String> row = tabela.asMaps().get(0);
        String nome = row.get("Nome");
        ResponsavelResponse responsavelResponse = inserirResponsavel(nome, nome + "@empresa.com");
        ProjetoRequest projetoRequest = ProjetoRequest
                .builder()
                .nome(nome)
                .responsavelId(responsavelResponse.getId())
                .status("S")
                .build();
        String payload = objectMapper.writeValueAsString(projetoRequest);

        MvcResult res = mockMvc.perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        ctx.ultimoStatus = res.getResponse().getStatus();
        ctx.ultimoBody = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProjetoResponse criado = objectMapper.readValue(ctx.ultimoBody, ProjetoResponse.class);
        ctx.ultimoIdSalvo = criado.getId();
    }

    @Então("o sistema deve retornar os dados de projetos vazios")
    public void retornarDadosDeProjetosVazios() throws Exception {
        assertEquals(200, ctx.ultimoStatus);
        JsonNode json = objectMapper.readTree(ctx.ultimoBody);
        assertTrue("Esperava array JSON", json.isArray());
        assertEquals("Lista deveria estar vazia", 0, json.size());
    }

    @Então("o sistema deve retornar os seguintes dados de projetos")
    public void retornarOsSeguintesDadosDeProjetos(DataTable tabelaEsperada) throws Exception {
        assertEquals(200, ctx.ultimoStatus);
        JsonNode json = objectMapper.readTree(ctx.ultimoBody);
        assertTrue("Esperava lista de projetos", json.isArray());
        List<String> esperados = new ArrayList<>();
        for (Map<String, String> row : tabelaEsperada.asMaps()) {
            esperados.add(row.get("Nome"));
        }
        assertEquals("Quantidade diferente da esperada", esperados.size(), json.size());
        for (int i = 0; i < esperados.size(); i++) {
            assertEquals("Nome na posição " + i + " diferente",
                    esperados.get(i), json.get(i).get("nome").asText());
        }
    }

    @Então("o sistema deve retornar o seguinte dado de projeto")
    public void retornarOSeguinteDadoDeProjeto(DataTable tabelaEsperada) throws Exception {
        JsonNode json = objectMapper.readTree(ctx.ultimoBody);
        assertTrue("Esperava objeto JSON", json.isObject());

        String esperado = tabelaEsperada.asMaps().get(0).get("Nome");
        assertEquals(esperado, json.get("nome").asText());
    }

    @Então("a resposta deve ser projeto não encontrado")
    public void respostaProjetoNaoEncontrado() {
        assertEquals(404, ctx.ultimoStatus);
    }

    @Então("o sistema deve apagar o projeto com sucesso")
    public void apagarProjetoComSucesso() {
        assertEquals(204, ctx.ultimoStatus);
    }

}
