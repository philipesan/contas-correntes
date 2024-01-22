package com.santanderbr.contas.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santanderbr.contas.dto.requests.TransactionRequestDTO;
import com.santanderbr.contas.dto.responses.TransactionResponseDTO;
import com.santanderbr.contas.entities.Transaction;

@Mapper(componentModel="spring")
public interface TransactionMapper {

	TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="transactionDate", source="transactionDate")
	TransactionResponseDTO toResponseDto(Transaction transaction);
	
	@Mapping(target="id", source="id")
	@Mapping(target="transactionDate", source="transactionDate")
	Transaction toEntity(TransactionRequestDTO transaction);
	

}
