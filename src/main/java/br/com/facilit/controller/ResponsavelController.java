package br.com.facilit.controller;

import br.com.facilit.dto.ResponsavelDTO;
import br.com.facilit.mapper.ResponsavelMapper;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ResponsavelResponse;
import br.com.facilit.service.ResponsavelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

    private final ResponsavelService service;
    private final ResponsavelMapper mapper;

    public ResponsavelController(ResponsavelService service, ResponsavelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ResponsavelResponse> consultarTodos() {
        List<ResponsavelDTO> responsaveisDtoList = service.consultarTodos();
        List<ResponsavelResponse> resposta = responsaveisDtoList.stream().map(mapper::toResponse).collect(Collectors.toList());
        return resposta;
    }

    @GetMapping("/{id}")
    public ResponsavelResponse buscar(@PathVariable Long id) {
        ResponsavelDTO responsavelDTO = service.buscarOu404(id);
        ResponsavelResponse resposta = mapper.toResponse(responsavelDTO);
        return resposta;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsavelResponse criar(@Valid @RequestBody ResponsavelRequest request) {
        ResponsavelDTO responsavelDTO = mapper.toDTO(request);
        ResponsavelDTO responsavelDTOCriado = service.criar(responsavelDTO);
        ResponsavelResponse resposta = mapper.toResponse(responsavelDTOCriado);
        return resposta;
    }

    @PutMapping("/{id}")
    public ResponsavelResponse atualizar(@PathVariable Long id, @Valid @RequestBody ResponsavelRequest request) {
        ResponsavelDTO responsavelDTO = mapper.toDTO(request);
        ResponsavelDTO responsavelDTOAtualizado = service.atualizar(id, responsavelDTO);
        ResponsavelResponse resposta = mapper.toResponse(responsavelDTOAtualizado);
        return resposta;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
