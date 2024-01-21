package com.santanderbr.contas.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santanderbr.contas.dto.requests.AccountRequestDTO;
import com.santanderbr.contas.dto.responses.AccountResponseDTO;
import com.santanderbr.contas.entities.Account;

@Mapper(componentModel="spring")
public interface AccountMapper {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="branch", source="branch")
	@Mapping(target="balance", source="balance")
	@Mapping(target="status", source="status")
	@Mapping(target="accountHolder", source="accountHolder")
	AccountResponseDTO toResponseDto(Account account);
	
	@Mapping(target="id", source="id")
	@Mapping(target="branch", source="branch")
	@Mapping(target="status", source="status")
	Account toEntity(AccountRequestDTO account);

}
