package br.com.facilit.controller;

import br.com.facilit.domain.Projeto;
import br.com.facilit.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Projeto> listar(@RequestParam(required = false) String status) {
        return service.listar(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto criar(@Valid @RequestBody Projeto p) {
        return service.criar(p);
    }
}
