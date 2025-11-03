package br.com.facilit.service;

import br.com.facilit.domain.Responsavel;
import br.com.facilit.dto.ResponsavelDTO;
import br.com.facilit.exception.NotFoundException;
import br.com.facilit.mapper.ResponsavelMapper;
import br.com.facilit.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponsavelService {
    private final ResponsavelMapper mapper;
    private final ResponsavelRepository repository;

    public ResponsavelService(ResponsavelMapper mapper, ResponsavelRepository repo) {
        this.mapper = mapper;
        this.repository = repo;
    }

    @Transactional(readOnly = true)
    public List<ResponsavelDTO> consultarTodos() {
        List<Responsavel> responsaveisList = repository.findAll();
        List<ResponsavelDTO> resposta = responsaveisList.stream().map(mapper::toDTO).collect(Collectors.toList());
        return resposta;
    }

    @Transactional(readOnly = true)
    public ResponsavelDTO buscarOu404(Long id) {
        Responsavel responsavel = repository.findById(id).orElseThrow(() -> new NotFoundException("Responsável não encontrado"));
        ResponsavelDTO resposta = mapper.toDTO(responsavel);
        return resposta;
    }

    @Transactional
    public ResponsavelDTO criar(ResponsavelDTO responsavelDTO) {
        Responsavel responsavel = mapper.toEntity(responsavelDTO);
        Responsavel responsavelCriado = repository.save(responsavel);
        ResponsavelDTO resposta = mapper.toDTO(responsavelCriado);
        return resposta;
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Responsável não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public ResponsavelDTO atualizar(Long id, ResponsavelDTO responsavelDTO) {
        Responsavel responsavelExistente = repository.findById(id).orElseThrow(() -> new NotFoundException("Responsável não encontrado"));
        mapper.update(responsavelExistente, responsavelDTO);
        responsavelExistente.setId(id);
        Responsavel responsavelSalvo = repository.save(responsavelExistente);
        ResponsavelDTO resposta = mapper.toDTO(responsavelSalvo);
        return resposta;
    }

}
