package com.santanderbr.contas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.services.OperationService;

@RestController
@RequestMapping(path="api/v1/operation")
public class OperationController {
	@Autowired
	OperationService operationService;
	
	@GetMapping(path="/search/{idOperation}")
	public ResponseEntity<ApiResponseDTO> getAccount(    		
			@PathVariable Long idOperation ) {
		return operationService.getOperation(idOperation);
	}
	
}
