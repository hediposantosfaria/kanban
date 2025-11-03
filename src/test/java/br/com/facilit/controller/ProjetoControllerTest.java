package br.com.facilit.controller;

import br.com.facilit.config.TestBeans;
import br.com.facilit.dto.ProjetoDTO;
import br.com.facilit.exception.NotFoundException;
import br.com.facilit.mapper.ProjetoMapper;
import br.com.facilit.request.ProjetoRequest;
import br.com.facilit.response.ProjetoResponse;
import br.com.facilit.service.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProjetoController.class)
@Import({TestBeans.class})
public class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjetoService projetoServiceMock;

    @Autowired
    private ProjetoMapper mapper;

    private final ProjetoResponse projetoResponse = ProjetoResponse.builder().id(0L).nome("XPTO").build();
    private final ProjetoDTO projetoDTO = ProjetoDTO.builder().id(0L).nome("XPTO").build();
    private final ProjetoRequest projetoRequest = ProjetoRequest.builder().nome("XPTO").build();

    @Test
    public void consultarTodosVazioTest() throws Exception {
        when(projetoServiceMock.consultarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void consultarTodosTest() throws Exception {
        when(projetoServiceMock.consultarTodos()).thenReturn(Collections.singletonList(projetoDTO));
        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].nome", is("XPTO")));
    }

    @Test
    public void buscarTest() throws Exception {
        when(projetoServiceMock.buscarOu404(0L)).thenReturn(projetoDTO);
        mockMvc.perform(get("/projetos/{id}", 0L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.nome", is("XPTO")));
    }

    @Test
    public void buscarComNotFoundTest() throws Exception {
        doThrow(new NotFoundException("Projeto não encontrado")).when(projetoServiceMock).buscarOu404(0L);
        mockMvc.perform(get("/projetos/{id}", 0L)).andExpect(status().isNotFound());
    }

    @Test
    public void atualizarTest() throws Exception {
        when(projetoServiceMock.atualizar(0L, projetoDTO)).thenReturn(projetoDTO);
        String payload = objectMapper.writeValueAsString(projetoRequest);
        MvcResult mvcResult = mockMvc.perform(put("/projetos/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void atualizarCom404Test() throws Exception {
        when(projetoServiceMock.atualizar(anyLong(), any(ProjetoDTO.class))).thenThrow(new NotFoundException("Projeto não encontrado"));
        String payload = objectMapper.writeValueAsString(projetoRequest);
        mockMvc.perform(put("/projetos/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removerTest() throws Exception {
        mockMvc.perform(delete("/projetos/{id}", 1L))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void removerCom404Test() throws Exception {
        doThrow(new NotFoundException("Projeto não encontrado")).when(projetoServiceMock).remover(eq(0L));
        mockMvc.perform(delete("/projetos/{id}", 0L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void inserirTest() throws Exception {
        when(projetoServiceMock.criar(any(ProjetoDTO.class))).thenReturn(projetoDTO);
        String payload = objectMapper.writeValueAsString(projetoRequest);
        mockMvc.perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("XPTO")));
    }

}
