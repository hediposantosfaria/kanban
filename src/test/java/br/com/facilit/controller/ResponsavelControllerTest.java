package br.com.facilit.controller;

import br.com.facilit.config.TestBeans;
import br.com.facilit.dto.ResponsavelDTO;
import br.com.facilit.exception.NotFoundException;
import br.com.facilit.mapper.ResponsavelMapper;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ResponsavelResponse;
import br.com.facilit.service.ResponsavelService;
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
@WebMvcTest(controllers = ResponsavelController.class)
@Import({TestBeans.class})
public class ResponsavelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResponsavelService responsavelServiceMock;

    @Autowired
    private ResponsavelMapper mapper;

    private final ResponsavelResponse responsavelResponse = ResponsavelResponse.builder().id(0L).nome("Bento").email("bento@empresa.com").build();
    private final ResponsavelDTO responsavelDTO = ResponsavelDTO.builder().id(0L).nome("Bento").email("bento@empresa.com").build();
    private final ResponsavelRequest responsavelRequest = ResponsavelRequest.builder().nome("Bento").email("bento@empresa.com").build();

    @Test
    public void consultarTodosVazioTest() throws Exception {
        when(responsavelServiceMock.consultarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void consultarTodosTest() throws Exception {
        when(responsavelServiceMock.consultarTodos()).thenReturn(Collections.singletonList(responsavelDTO));
        mockMvc.perform(get("/responsaveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].nome", is("Bento")))
                .andExpect(jsonPath("$[0].email", is("bento@empresa.com")));
    }

    @Test
    public void buscarTest() throws Exception {
        when(responsavelServiceMock.buscarOu404(0L)).thenReturn(responsavelDTO);
        mockMvc.perform(get("/responsaveis/{id}", 0L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.nome", is("Bento")))
                .andExpect(jsonPath("$.email", is("bento@empresa.com")));
    }

    @Test
    public void buscarComNotFoundTest() throws Exception {
        doThrow(new NotFoundException("Responsável não encontrado")).when(responsavelServiceMock).buscarOu404(0L);
        mockMvc.perform(get("/responsaveis/{id}", 0L)).andExpect(status().isNotFound());
    }

    @Test
    public void atualizarTest() throws Exception {
        when(responsavelServiceMock.atualizar(0L, responsavelDTO)).thenReturn(responsavelDTO);
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        MvcResult mvcResult = mockMvc.perform(put("/responsaveis/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void atualizarCom404Test() throws Exception {
        when(responsavelServiceMock.atualizar(anyLong(), any(ResponsavelDTO.class))).thenThrow(new NotFoundException("Responsável não encontrado"));
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        mockMvc.perform(put("/responsaveis/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removerTest() throws Exception {
        mockMvc.perform(delete("/responsaveis/{id}", 1L))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void removerCom404Test() throws Exception {
        doThrow(new NotFoundException("Responsável não encontrado")).when(responsavelServiceMock).remover(eq(0L));
        mockMvc.perform(delete("/responsaveis/{id}", 0L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void inserirTest() throws Exception {
        when(responsavelServiceMock.criar(any(ResponsavelDTO.class))).thenReturn(responsavelDTO);
        String payload = objectMapper.writeValueAsString(responsavelRequest);
        mockMvc.perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Bento")))
                .andExpect(jsonPath("$.email", is("bento@empresa.com")));
    }

}
