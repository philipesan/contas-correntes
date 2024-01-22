package com.santanderbr.contas.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.santanderbr.contas.BusinessException;
import com.santanderbr.contas.dto.requests.OperationRequestDTO;
import com.santanderbr.contas.dto.requests.TransactionRequestDTO;
import com.santanderbr.contas.dto.responses.AccountResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.dto.responses.OperationResponseDTO;
import com.santanderbr.contas.dto.responses.TransactionResponseDTO;
import com.santanderbr.contas.entities.Account;
import com.santanderbr.contas.entities.Operation;
import com.santanderbr.contas.entities.Transaction;
import com.santanderbr.contas.mapstruct.AccountMapper;
import com.santanderbr.contas.mapstruct.AddressMapper;
import com.santanderbr.contas.mapstruct.ClientMapper;
import com.santanderbr.contas.mapstruct.OperationMapper;
import com.santanderbr.contas.mapstruct.TransactionMapper;
import com.santanderbr.contas.repositories.AccountRepository;
import com.santanderbr.contas.repositories.TransactionRepository;
import com.santanderbr.contas.services.OperationService;
import com.santanderbr.contas.services.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	@Autowired TransactionRepository transactionRepository;
	@Autowired TransactionMapper transactionMapper;
	@Autowired AccountMapper accountMapper;
	@Autowired ClientMapper clientMapper;
	@Autowired AddressMapper addressMapper;
	@Autowired OperationMapper operationMapper;
	@Autowired OperationService operationService;
	@Autowired AccountRepository accountRepository;
	
	@Override
	public ResponseEntity<ApiResponseDTO> getTransaction(String param, String value) {
		ApiResponseDTO apiResponse = new ApiResponseDTO();

		
		if(param.equals("id")) {
			Optional<Transaction> transaction;
			TransactionResponseDTO transResponse;
			Long id = Long.valueOf(value);
			transaction = transactionRepository.findById(id);
			
			if(transaction.isEmpty()) {
				apiResponse.setMessage("Transaction not found");
				return ResponseEntity.status(422).body(apiResponse);
			} 
			
			transResponse = transactionMapper.toResponseDto(transaction.get());
			transResponse.setAccount(accountMapper.toResponseDto(transaction.get().getAccount()));

			List<Operation> operations;
			operations = transaction.get().getOperations();
			List<OperationResponseDTO> operationsDto = operationService.convertOperations(operations);
			
			transResponse.setOperations(operationsDto);
			
			apiResponse.setContent(transResponse);
			
		} else if(param.equals("account")) {
			List<Transaction> transactions;
			List<TransactionResponseDTO> transResponse;
			transactions = transactionRepository.findAllByAccountId(Long.valueOf(value));
			
			if(transactions.isEmpty()) {
				apiResponse.setMessage("Transaction not found");
				return ResponseEntity.status(422).body(apiResponse);
			} 
			
			transResponse = transactions.stream().map(transaction -> {
				TransactionResponseDTO transResponseDto = transactionMapper.toResponseDto(transaction);
				transResponseDto.setAccount(accountMapper.toResponseDto(transaction.getAccount()));
				transResponseDto.setOperations(operationService.convertOperations(transaction.getOperations()));
				return transResponseDto;
			}).collect(Collectors.toList());
			
			apiResponse.setContent(transResponse);
			
		
		} else {
			apiResponse.setMessage("Invalid request Parameter: " + param);
			return ResponseEntity.status(422).body(apiResponse);
		}

		apiResponse.setMessage("Client found!");
		return ResponseEntity.status(200).body(apiResponse);	

	}

	@Override
	public ResponseEntity<ApiResponseDTO> createTransaction(TransactionRequestDTO transaction) {
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Transaction transactionEntity = transactionMapper.toEntity(transaction);
		TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
		AccountResponseDTO accountResponse = new AccountResponseDTO();
		ClientResponseDTO clientResponse = new ClientResponseDTO();

		Optional<Account> account = accountRepository.findById(transaction.getAccountId());
		
		if(account.isEmpty()) {
			apiResponse.setMessage("Account not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		
		List<OperationRequestDTO> operationsDto = transaction.getOperations();	
		List<Operation> operations = operationsDto.stream()
									.map(operationDto -> {
										Operation operation;
										operation = operationMapper.toEntity(operationDto);
										operation.setAccount(accountRepository.findById(operationDto.getAccountId()).get());
										
										return operation;
									})
									.collect(Collectors.toList());
		
		try {
			operations = operationService.makeOperations(operations);
		} catch (BusinessException e) {
			apiResponse.setMessage("Business Error: " + e.getMessage());
			return ResponseEntity.status(422).body(apiResponse);
		} catch (Exception e) {
			
			apiResponse.setMessage("Error During operations: " + e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}
		

		transactionEntity.setAccount(account.get());
		transactionEntity.setOperations(operations);
		transactionEntity = transactionRepository.save(transactionEntity);
		
		
		clientResponse = clientMapper.toResponseDto(transactionEntity.getAccount().getAccountHolder()) ;
		clientResponse.setAddress(transactionEntity.getAccount().getAccountHolder().getAddress());
		
		accountResponse = accountMapper.toResponseDto(transactionEntity.getAccount());
		accountResponse.setAccountHolder(clientResponse);
		transactionResponse.setOperations(operationService.convertOperations(transactionEntity.getOperations()));
		transactionResponse.setAccount(accountResponse);
		apiResponse.setContent(transactionResponse);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@Override
	public ResponseEntity<ApiResponseDTO> rollbackTransaction(Long transactionId) {
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Optional<Transaction> transaction = transactionRepository.findById(transactionId);
		
		if(transaction.isEmpty()) {
			apiResponse.setMessage("Transaction not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		List<Operation> operations = transaction.get().getOperations();
		
		try {
			operations = operationService.rollbackOperations(operations);
		} catch (BusinessException e) {
			apiResponse.setMessage("Business Error: " + e.getMessage());
			return ResponseEntity.status(422).body(apiResponse);
		} catch (Exception e) {
			
			apiResponse.setMessage("Error During operations: " + e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}
		
		transaction.get().setOperations(operations);
		transactionRepository.save(transaction.get());
		
		apiResponse.setContent("Rollback Completed");
		return ResponseEntity.status(200).body(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponseDTO> renotifyTransaction(Long idTrans) {
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Optional<Transaction> transaction = transactionRepository.findById(idTrans);
		
		if(transaction.isEmpty()) {
			apiResponse.setMessage("Transaction not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		List<Operation> operations = transaction.get().getOperations();
		
		operations.stream().forEach(operation -> {
			if(operation.getNotificationFlag().equals(true) && !operation.getStatus().equals(3)) {
				ResponseEntity<String> response = operationService.notifyService(operation);
				if(!(response.getStatusCode() == HttpStatus.OK)) {
					log.warn("O serviço de Notificação de operação retornou um erro: " + response.getStatusCode().value());
				} else {
					log.info("Usuário notificado, alterando status da operação para 2 - Notificado");
					operation.setNotificationDate(LocalDate.now());
					operation.setStatus(2);
				}
			}
		});
		
		apiResponse.setContent("Reprocessing Completed");
		return ResponseEntity.status(200).body(apiResponse);
	}
}
