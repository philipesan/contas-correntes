package com.santanderbr.contas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.services.ClientService;

@RestController
@RequestMapping(path="api/v1/client")
public class ClientController {
	@Autowired
	ClientService clientService;
	
	@GetMapping(path="/{idClient}")
	public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long idClient) {
		return clientService.getClient(idClient);
	}
}
