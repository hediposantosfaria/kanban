package br.com.facilit.controller;

import br.com.facilit.domain.Responsavel;
import br.com.facilit.repository.ResponsavelRepository;
import br.com.facilit.service.ResponsavelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {
    private final ResponsavelService service;

    public ResponsavelController(ResponsavelRepository repo, ResponsavelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Responsavel> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Responsavel criar(@Valid @RequestBody Responsavel r) {
        return service.criar(r);
    }
}
