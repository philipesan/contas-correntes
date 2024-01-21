package com.santanderbr.contas.services.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.santanderbr.contas.dto.requests.AccountRequestDTO;
import com.santanderbr.contas.dto.responses.AccountResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.entities.Account;
import com.santanderbr.contas.entities.Client;
import com.santanderbr.contas.mapstruct.AccountMapper;
import com.santanderbr.contas.repositories.AccountRepository;
import com.santanderbr.contas.repositories.ClientRepository;
import com.santanderbr.contas.services.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccountServiceImpl implements AccountService {
	@Autowired AccountRepository accountRepository;
	@Autowired AccountMapper accountMapper;
	@Autowired ClientRepository clientRepository;

	@Override
	public ResponseEntity<ApiResponseDTO> getAccount(Long idAccount) {
		AccountResponseDTO response = new AccountResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Optional<Account> account = accountRepository.findById(idAccount);
		
		if(account.isEmpty()) {
			apiResponse.setMessage("Account not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		response = accountMapper.toResponseDto(account.get());
		apiResponse.setMessage("Account Found!");
		apiResponse.setContent(response);
		return ResponseEntity.status(200).body(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponseDTO> listAccount(Integer page, Integer items, String sort, String direction,
			Integer status) {

		Pageable pageable;
		if (direction == "0") {
			pageable = PageRequest.of(page, items, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, items, Sort.by(sort).descending());
		}
		
		Page<AccountResponseDTO> search;
		
		if(status.equals(3)) {
			search = accountRepository.findAll(pageable).map(accountMapper::toResponseDto);
		} else {
			search = accountRepository.findAllByStatus(pageable, status).map(accountMapper::toResponseDto);			
		}
		
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		apiResponse.setMessage("Listing Results");
		apiResponse.setContent(search);
		return ResponseEntity.status(200).body(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponseDTO> createAccount(AccountRequestDTO account) 
	{
		AccountResponseDTO response = new AccountResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		
		log.warn("Registering an account for client: "+ account.getAccountHolderId());
		
		Optional<Client> client = this.locateClient(account.getAccountHolderId());
		
		if(client.isEmpty()) {
			log.warn("Client: " + account.getAccountHolderId() + " not found");
			apiResponse.setMessage("Address could not be found");
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		Account newAccount = accountMapper.toEntity(account);
		newAccount.setStatus(0);
		newAccount.setBalance(BigDecimal.valueOf(0));
		newAccount.setAccountHolder(client.get());
		
		try {
			newAccount = accountRepository.save(newAccount);
			response = accountMapper.toResponseDto(newAccount);
			apiResponse.setMessage("Account created successfully!");
			apiResponse.setContent(response);
			log.info("New entry created: " + response.getId());
			return ResponseEntity.status(201).body(apiResponse);
		} catch(Exception e) {
			apiResponse.setMessage(e.getMessage());
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}

	}

	@Override
	public ResponseEntity<ApiResponseDTO> updateAccount(@Valid AccountRequestDTO account, Long idAccount) {
		AccountResponseDTO response = new AccountResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		
		log.warn("Updating account: "+ idAccount);
				
		Optional<Account> validateAccount = accountRepository.findById(idAccount);
		if(validateAccount.isEmpty()) {
			log.warn("Account: " + idAccount + " not found");
			apiResponse.setMessage("Acccount could not be found");
			return ResponseEntity.status(422).body(apiResponse);
		}
				
		Optional<Client> client = this.locateClient(account.getAccountHolderId());		
		if(client.isEmpty()) {
			log.warn("Client: " + account.getAccountHolderId() + " not found");
			apiResponse.setMessage("Address could not be found");
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		Account newAccount = accountMapper.toEntity(account);
		newAccount.setAccountHolder(client.get());
		
		try {
			newAccount = accountRepository.save(newAccount);
			response = accountMapper.toResponseDto(newAccount);
			apiResponse.setMessage("Client created successfully!");
			apiResponse.setContent(response);
			log.info("New entry created: " + response.getId());
			return ResponseEntity.status(201).body(apiResponse);
		} catch(Exception e) {
			apiResponse.setMessage(e.getMessage());
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}

	}
	private Optional<Client> locateClient(Long clientId) {
		Optional<Client> client = clientRepository.findById(clientId);
		return client;
	}

}
