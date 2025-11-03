package br.com.facilit.config;

import br.com.facilit.mapper.ProjetoMapper;
import br.com.facilit.mapper.ResponsavelMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

public class TestBeans {
    @Bean
    public ResponsavelMapper responsavelMapper() {
        ResponsavelMapper mapper = Mappers.getMapper(ResponsavelMapper.class);
        return mapper;
    }

    @Bean
    public ProjetoMapper projetoMapper() {
        ProjetoMapper mapper = Mappers.getMapper(ProjetoMapper.class);
        return mapper;
    }
}
