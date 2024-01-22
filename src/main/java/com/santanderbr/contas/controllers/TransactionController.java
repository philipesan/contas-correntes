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

import com.santanderbr.contas.dto.requests.TransactionRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/transaction")
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	@GetMapping(path="/search")
	public ResponseEntity<ApiResponseDTO> getTransaction(    		
			@RequestParam(defaultValue = "id") String param,
    		@RequestParam(defaultValue = "0") String value) {
		return transactionService.getTransaction(param, value);
	}
	
	@PatchMapping(path="/rollback/{idTrans}")
    public ResponseEntity<ApiResponseDTO> rollbackTransaction(@PathVariable Long idTrans) {
		return transactionService.rollbackTransaction(idTrans);
	}
	
	@PatchMapping(path="/renotify/{idTrans}")
    public ResponseEntity<ApiResponseDTO> renotifyTransaction(@PathVariable Long idTrans) {
		return transactionService.renotifyTransaction(idTrans);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transaction) {
		return transactionService.createTransaction(transaction);
	}
}
