package com.santanderbr.contas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santanderbr.contas.dto.requests.AccountRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;

import jakarta.validation.Valid;

@Service
public interface AccountService {

	ResponseEntity<ApiResponseDTO> listAccount(Integer page, Integer items, String sort, String direction,
			Integer status);

	ResponseEntity<ApiResponseDTO> createAccount(AccountRequestDTO account);

	ResponseEntity<ApiResponseDTO> updateAccount(AccountRequestDTO account, Long idAccount);

	ResponseEntity<ApiResponseDTO> getAccount(String param, String value);

}
