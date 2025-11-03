package br.com.facilit.service;

import br.com.facilit.domain.Projeto;
import br.com.facilit.dto.ProjetoDTO;
import br.com.facilit.exception.NotFoundException;
import br.com.facilit.mapper.ProjetoMapper;
import br.com.facilit.repository.ProjetoRepository;
import br.com.facilit.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    private final ProjetoMapper mapper;
    private final ProjetoRepository repository;
    private final ResponsavelRepository repositoryResponsavel;

    public ProjetoService(ProjetoMapper mapper, ProjetoRepository repo, ResponsavelRepository repositoryResponsavel) {
        this.mapper = mapper;
        this.repository = repo;
        this.repositoryResponsavel = repositoryResponsavel;
    }

    @Transactional(readOnly = true)
    public List<ProjetoDTO> consultarTodos() {
        List<Projeto> projetosList = repository.findAll();
        List<ProjetoDTO> resposta = projetosList.stream().map(mapper::toDTO).collect(Collectors.toList());
        return resposta;
    }

    @Transactional(readOnly = true)
    public List<Projeto> listarPorStatus(String status) {
        return repository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public ProjetoDTO buscarOu404(Long id) {
        Projeto projeto = repository.findById(id).orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        ProjetoDTO resposta = mapper.toDTO(projeto);
        return resposta;
    }

    @Transactional
    public ProjetoDTO criar(ProjetoDTO projetoDTO) {
        Projeto projeto = mapper.toEntity(projetoDTO);
        Projeto projetoCriado = repository.save(projeto);
        ProjetoDTO resposta = mapper.toDTO(projetoCriado);
        return resposta;
    }

    @Transactional
    public ProjetoDTO atualizar(Long id, ProjetoDTO projetoDTO) {
        Projeto projetoExistente = repository.findById(id).orElseThrow(() -> new NotFoundException("Projeto não encontrado"));

        if (projetoDTO.getResponsavel() != null && !repositoryResponsavel.existsById(projetoDTO.getResponsavel().getId())) {
            throw new NotFoundException("Responsável não encontrado");
        }

        mapper.update(projetoExistente, projetoDTO);
        projetoExistente.setId(id);
        Projeto projetoSalvo = repository.save(projetoExistente);
        ProjetoDTO resposta = mapper.toDTO(projetoSalvo);
        return resposta;
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Projeto não encontrado");
        }
        repository.deleteById(id);
    }
}
