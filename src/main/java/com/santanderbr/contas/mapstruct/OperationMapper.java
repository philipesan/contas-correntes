package com.santanderbr.contas.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santanderbr.contas.dto.requests.OperationRequestDTO;
import com.santanderbr.contas.dto.responses.OperationResponseDTO;
import com.santanderbr.contas.entities.Operation;

@Mapper(componentModel="spring")
public interface OperationMapper {
	TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="operationLine", source="operationLine")
	@Mapping(target="operationDate", source="operationDate")
	@Mapping(target="amount", source="amount")
	@Mapping(target="notificationFlag", source="notificationFlag")
	@Mapping(target="notificationDate", source="notificationDate")
	@Mapping(target="status", source="status")
	OperationResponseDTO toResponseDto(Operation operation);
	
	@Mapping(target="amount", source="amount")
	@Mapping(target="operationLine", source="operationLine")
	@Mapping(target="operationDate", source="operationDate")
	@Mapping(target="notificationFlag", source="notificationFlag")
	Operation toEntity(OperationRequestDTO operation);
	
}
