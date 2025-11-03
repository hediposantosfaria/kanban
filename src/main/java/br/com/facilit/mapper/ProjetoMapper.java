package br.com.facilit.mapper;

import br.com.facilit.domain.Projeto;
import br.com.facilit.domain.Responsavel;
import br.com.facilit.dto.ProjetoDTO;
import br.com.facilit.dto.ResponsavelDTO;
import br.com.facilit.request.ProjetoRequest;
import br.com.facilit.response.ProjetoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "responsavel", source = "responsavel.id", qualifiedByName = "toResponsavel")
    Projeto toEntity(ProjetoDTO dto);

    @Mapping(target = "responsavel", source = "responsavelId", qualifiedByName = "toResponsavelDTO")
    ProjetoDTO toDTO(ProjetoRequest request);

    @Mapping(target = "responsavel", source = "responsavel.id", qualifiedByName = "toResponsavelDTO")
    ProjetoDTO toDTO(Projeto entity);

    @Mapping(target = "responsavelId", source = "responsavel.id")
    ProjetoResponse toResponse(ProjetoDTO dto);

    @Mapping(target = "responsavel", source = "responsavel.id", qualifiedByName = "toResponsavel")
    void update(@MappingTarget Projeto target, ProjetoDTO source);

    @Named("toResponsavel")
    default Responsavel toResponsavel(Long id) {
        if (id == null) {
            return null;
        }
        Responsavel r = new Responsavel();
        r.setId(id);
        return r;
    }

    @Named("toResponsavelDTO")
    default ResponsavelDTO toResponsavelDTO(Long id) {
        if (id == null) {
            return null;
        }
        return ResponsavelDTO.builder().id(id).build();
    }

}
