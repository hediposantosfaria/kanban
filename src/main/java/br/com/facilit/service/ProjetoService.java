package br.com.facilit.service;

import br.com.facilit.domain.Projeto;
import br.com.facilit.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoService {
    private final ProjetoRepository repo;

    public ProjetoService(ProjetoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Projeto criar(Projeto p) {
        if (p.getStatus() == null || p.getStatus().trim().isEmpty()) {
            p.setStatus("A_INICIAR");
        }
        return repo.save(p);
    }

    public List<Projeto> listar(String status) {
        return status == null ? repo.findAll() : repo.findByStatus(status);
    }
}
