package com.santanderbr.contas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santanderbr.contas.dto.requests.AccountRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/account")
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@GetMapping(path="/search")
	public ResponseEntity<ApiResponseDTO> getAccount(    		
			@RequestParam(defaultValue = "id") String param,
    		@RequestParam(defaultValue = "0") String value) {
		return accountService.getAccount(param, value);
	}
	
	@GetMapping(path="/list")
    public ResponseEntity<ApiResponseDTO> listClient(@RequestParam Integer page, 
    		@RequestParam Integer items,
    		@RequestParam(defaultValue = "id") String sort,
    		@RequestParam(defaultValue = "0") String direction,
    		@RequestParam(defaultValue = "3") Integer status) {
		return accountService.listAccount(page, items, sort, direction, status);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDTO> createClient(@Valid @RequestBody AccountRequestDTO account) {
		return accountService.createAccount(account);
	}
	
	@PatchMapping(path="/{idAccount}")
	public ResponseEntity<ApiResponseDTO> updateAddress(@PathVariable Long idAccount, @Valid @RequestBody AccountRequestDTO account) {
		return accountService.updateAccount(account, idAccount);
	}
}
