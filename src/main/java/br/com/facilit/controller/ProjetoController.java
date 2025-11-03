package br.com.facilit.controller;

import br.com.facilit.dto.ProjetoDTO;
import br.com.facilit.mapper.ProjetoMapper;
import br.com.facilit.request.ProjetoRequest;
import br.com.facilit.response.ProjetoResponse;
import br.com.facilit.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService service;
    private final ProjetoMapper mapper;

    public ProjetoController(ProjetoService service, ProjetoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProjetoResponse> consultarTodos() {
        List<ProjetoDTO> projetosDtoList = service.consultarTodos();
        List<ProjetoResponse> resposta = projetosDtoList.stream().map(mapper::toResponse).collect(Collectors.toList());
        return resposta;
    }

    @GetMapping("/{id}")
    public ProjetoResponse buscar(@PathVariable Long id) {
        ProjetoDTO projetoDTO = service.buscarOu404(id);
        ProjetoResponse resposta = mapper.toResponse(projetoDTO);
        return resposta;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoResponse criar(@Valid @RequestBody ProjetoRequest request) {
        ProjetoDTO projetoDTO = mapper.toDTO(request);
        ProjetoDTO projetoDTOCriado = service.criar(projetoDTO);
        ProjetoResponse resposta = mapper.toResponse(projetoDTOCriado);
        return resposta;
    }

    @PutMapping("/{id}")
    public ProjetoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProjetoRequest request) {
        ProjetoDTO projetoDTO = mapper.toDTO(request);
        ProjetoDTO projetoDTOAtualizado = service.atualizar(id, projetoDTO);
        ProjetoResponse resposta = mapper.toResponse(projetoDTOAtualizado);
        return resposta;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

}
