package br.com.facilit.service;

import br.com.facilit.domain.Responsavel;
import br.com.facilit.exception.NegocioException;
import br.com.facilit.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResponsavelService {
    private final ResponsavelRepository repo;

    public ResponsavelService(ResponsavelRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Responsavel criar(Responsavel r) {
        if (repo.existsByEmail(r.getEmail()))
            throw new NegocioException("E-mail já cadastrado: " + r.getEmail());
        return repo.save(r);
    }

    public List<Responsavel> listar() {
        return repo.findAll();
    }
}
