package com.santanderbr.contas.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santanderbr.contas.dto.requests.AddressRequestDTO;
import com.santanderbr.contas.dto.responses.AddressResponseDTO;
import com.santanderbr.contas.entities.Address;

@Mapper(componentModel="spring")
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="zipCode", source="zipCode")
	@Mapping(target="streetName", source="streetName")
	@Mapping(target="number", source="number")
	@Mapping(target="city", source="city")
	@Mapping(target="country", source="country")
	@Mapping(target="neighborhood", source="neighborhood")
	@Mapping(target="stateSymbol", source="stateSymbol")
	@Mapping(target="complement", source="complement")
	@Mapping(target="status", source="status")
	AddressResponseDTO toResponseDto(Address address);
	
	@Mapping(target="id", source="id")
	@Mapping(target="zipCode", source="zipCode")
	@Mapping(target="streetName", source="streetName")
	@Mapping(target="number", source="number")
	@Mapping(target="city", source="city")
	@Mapping(target="country", source="country")
	@Mapping(target="neighborhood", source="neighborhood")
	@Mapping(target="stateSymbol", source="stateSymbol")
	@Mapping(target="complement", source="complement")
	@Mapping(target="status", source="status")
	Address toEntity(AddressRequestDTO address);
}
