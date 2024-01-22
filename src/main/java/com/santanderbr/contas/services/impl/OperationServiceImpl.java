package com.santanderbr.contas.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.santanderbr.contas.BusinessException;
import com.santanderbr.contas.dto.requests.NotificationServiceRequestDTO;
import com.santanderbr.contas.dto.responses.AccountResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.dto.responses.OperationResponseDTO;
import com.santanderbr.contas.entities.Account;
import com.santanderbr.contas.entities.Operation;
import com.santanderbr.contas.entities.Transaction;
import com.santanderbr.contas.mapstruct.AccountMapper;
import com.santanderbr.contas.mapstruct.ClientMapper;
import com.santanderbr.contas.mapstruct.OperationMapper;
import com.santanderbr.contas.mapstruct.TransactionMapper;
import com.santanderbr.contas.repositories.AccountRepository;
import com.santanderbr.contas.repositories.OperationRepository;
import com.santanderbr.contas.repositories.TransactionRepository;
import com.santanderbr.contas.services.OperationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OperationServiceImpl implements OperationService {
	@Autowired TransactionMapper transactionMapper;
	@Autowired TransactionRepository transactionRepository;
	@Autowired AccountMapper accountMapper;
	@Autowired OperationMapper operationMapper;
	@Autowired OperationRepository operationRepository;
	@Autowired ClientMapper clientMapper;
	@Autowired AccountRepository accountRepository;
	
	private final String urlNotification = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";
	//private final String urlNotification = "https://run.m9ff5-91f63010c9d3";

	@Autowired private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<ApiResponseDTO> getOperation(Long idOperation) {

		ApiResponseDTO apiResponse = new ApiResponseDTO();
	
		Optional<Operation> operation;
		OperationResponseDTO opResponseDto;
		AccountResponseDTO accountResponse;
		ClientResponseDTO clientResponse;
		
		
		operation = operationRepository.findById(idOperation);
		
		if(operation.isEmpty()) {
			apiResponse.setMessage("Operation not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
			
		opResponseDto = operationMapper.toResponseDto(operation.get());
		clientResponse = clientMapper.toResponseDto(
				operation.get().getAccount().getAccountHolder()); 
		
		accountResponse = accountMapper.toResponseDto(operation.get().getAccount());
		
		accountResponse.setAccountHolder(clientResponse);
		opResponseDto.setAccount(accountResponse);
	
		apiResponse.setContent(opResponseDto);


		apiResponse.setMessage("Operation found!");
		return ResponseEntity.status(200).body(apiResponse);	

	}

	@Override
	public List<Operation> makeOperations(List<Operation> operations) throws BusinessException {
		for (Operation operation : operations) {
			try {
				this.validateOperation(operation);
			
			} catch (BusinessException e) {
				throw e;
			}
		}
		
		operations.stream().map(operation -> {
			try {
				
				operation = this.actualizeOperation(operation);
				
			} catch (Exception e) {
				
				log.warn("Error during operations, the transaction will be rolled back");
				log.info(e.getStackTrace().toString());
				e.printStackTrace();
				throw e;
			}
			return operation;
		}).collect(Collectors.toList());
		
		return operations;
	}

	@Override
	public ResponseEntity<String> notifyService(Operation operation) {

			NotificationServiceRequestDTO notification = new NotificationServiceRequestDTO();
			notification.setAccountBranch(operation.getAccount().getBranch());
			notification.setAccountId(operation.getAccount().getId());
			notification.setNotification("Operação no valor de: " + operation.getAmount() + " realizada!");
			
			HttpEntity<String> request = new HttpEntity<>(notification.toString());
		try {    
			ResponseEntity<String> response = executeRequest(urlNotification, request);
			return response;
		} catch (HttpStatusCodeException e) {
			log.warn("Erro identificado no serviço de notificação: " + e.getMessage());
			return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders())
	                .body(e.getResponseBodyAsString());
		} catch (ResourceAccessException e) {
			log.warn("Erro identificado no serviço de notificação: " + e.getMessage());
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
    public ResponseEntity<String> executeRequest(String uri, HttpEntity<String> requestBody) {
    	ResponseEntity<String> response = this.restTemplate.postForEntity(uri, requestBody, String.class);
    	return response;
    }
	
	@Override
	public List<OperationResponseDTO> convertOperations(List<Operation> operations) {
		List<OperationResponseDTO> operationsDto;
		operationsDto = operations.stream().map(operation -> {
			AccountResponseDTO account = accountMapper.toResponseDto((operation.getAccount()));
			OperationResponseDTO operationDTO = operationMapper.toResponseDto(operation);
			operationDTO.setAccount(account);
			return operationDTO;
		}).collect(Collectors.toList());
		return operationsDto;
	}
	
	private Boolean validateOperation(Operation operation) throws BusinessException {
		if(!operation.getAccount().hasBalance(operation.getAmount())) throw new BusinessException("The account in question has no balance");
		if(operation.getAccount().getStatus().equals(1)) throw new BusinessException("The account in the Operation is blocked");
		if(!operation.getAccount().getAccountHolder().validateAddress()) throw new BusinessException("One of the Clients have an address is invalid");		
		if(this.accountHolderExists(operation.getAccount().getId())) throw new BusinessException("Account doesn't exist");		

		return true;
	}
	
	private Operation actualizeOperation(Operation operation) {
		try {
			operation.setStatus(0);
			log.info("Iniciando processamento da operacao: " + operation.getId());
			log.info("Mudando o status da operacao para 0 - pendente");
			operation.getAccount().adjustBalance(operation.getAmount());
			log.info("Ajustado o saldo, alterando para status 1 - notificar");
			operation.setStatus(1);			
			if(operation.getNotificationFlag()) {
				log.info("Iniciando notificação do usuário");
				ResponseEntity<String> response = this.notifyService(operation);
				if(!(response.getStatusCode() == HttpStatus.OK)) {
					log.warn("O serviço de Notificação de operação retornou um erro: " + response.getStatusCode().value());
				} else {
					log.info("Usuário notificado, alterando status da operação para 2 - Notificado");
					operation.setNotificationDate(LocalDate.now());
					operation.setStatus(2);
				}
			} else {
				operation.setStatus(2);
			}
			
			
			return operation;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<Operation> rollbackOperations(List<Operation> operations) throws BusinessException {
		for (Operation operation : operations) {
			try {
				this.validateOperation(operation);
			
			} catch (BusinessException e) {
				throw e;
			}
		}
		
		operations = operations.stream().map(operation -> {
				if(operation.getStatus().equals(1) || operation.getStatus().equals(2)) {
					operation.setStatus(3);
					operation.getAccount().setBalance(operation.getAccount().getBalance().add(operation.getAmount().multiply(BigDecimal.valueOf(-1))));
					this.notifyService(operation);
					operation.setNotificationDate(LocalDate.now());
				} else if(operation.getStatus() == 0) {
					operation.setStatus(3);
				} return operation;
			}).collect(Collectors.toList());
		
		return operations;
	}
	
	private Boolean accountHolderExists(Long accountId) {
		Optional<Account> account = accountRepository.findById(accountId);
		return account.isEmpty();
	}
}
