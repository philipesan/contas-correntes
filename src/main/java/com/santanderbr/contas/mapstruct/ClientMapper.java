package com.santanderbr.contas.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santanderbr.contas.dto.requests.ClientRequestDTO;
import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.entities.Client;


@Mapper(componentModel="spring")
public interface ClientMapper {
	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="name", source="name")
	@Mapping(target="address", source="address")
	@Mapping(target="status", source="status")
	ClientResponseDTO toResponseDto(Client client);
	
	@Mapping(target="id", source="id")
	@Mapping(target="name", source="name")
	@Mapping(target="status", source="status")
	Client toEntity(ClientRequestDTO client);
	
}
