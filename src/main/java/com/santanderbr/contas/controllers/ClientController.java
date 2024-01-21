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

import com.santanderbr.contas.dto.requests.AddressRequestDTO;
import com.santanderbr.contas.dto.requests.ClientRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/client")
public class ClientController {
	@Autowired
	ClientService clientService;
	
	@GetMapping(path="/search")
	public ResponseEntity<ApiResponseDTO> getClient(    		
			@RequestParam(defaultValue = "id") String param,
    		@RequestParam(defaultValue = "0") String value) {
		return clientService.getClient(param, value);
	}
	
	@GetMapping(path="/list")
    public ResponseEntity<ApiResponseDTO> listClient(@RequestParam Integer page, 
    		@RequestParam Integer items,
    		@RequestParam(defaultValue = "id") String sort,
    		@RequestParam(defaultValue = "0") String direction,
    		@RequestParam(defaultValue = "3") Integer status) {
		return clientService.listClient(page, items, sort, direction, status);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO client) {
		return clientService.createClient(client);
	}
	
	@PatchMapping(path="/{idClient}")
	public ResponseEntity<ApiResponseDTO> updateAddress(@PathVariable Long idClient, @Valid @RequestBody ClientRequestDTO client) {
		return clientService.updateClient(client, idClient);
	}
}
