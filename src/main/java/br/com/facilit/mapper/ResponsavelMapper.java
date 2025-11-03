package br.com.facilit.mapper;

import br.com.facilit.domain.Responsavel;
import br.com.facilit.dto.ResponsavelDTO;
import br.com.facilit.request.ResponsavelRequest;
import br.com.facilit.response.ResponsavelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper {

    Responsavel toEntity(ResponsavelDTO responsavelDTO);

    ResponsavelDTO toDTO(ResponsavelRequest request);

    ResponsavelDTO toDTO(Responsavel responsavel);

    ResponsavelResponse toResponse(ResponsavelDTO responsavelDTO);

    void update(@MappingTarget Responsavel target, ResponsavelDTO source);

}

