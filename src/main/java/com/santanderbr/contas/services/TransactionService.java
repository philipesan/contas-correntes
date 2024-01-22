package com.santanderbr.contas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santanderbr.contas.dto.requests.TransactionRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;

import jakarta.validation.Valid;

@Service
public interface TransactionService {

	ResponseEntity<ApiResponseDTO> getTransaction(String param, String value);

	ResponseEntity<ApiResponseDTO> createTransaction(TransactionRequestDTO transaction);

	ResponseEntity<ApiResponseDTO> rollbackTransaction(Long transactionId);

	ResponseEntity<ApiResponseDTO> renotifyTransaction(Long idTrans);

}
