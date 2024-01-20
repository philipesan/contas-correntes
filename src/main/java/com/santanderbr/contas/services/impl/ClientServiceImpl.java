package com.santanderbr.contas.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.entities.Client;
import com.santanderbr.contas.mapstruct.ClientMapper;
import com.santanderbr.contas.repositories.ClientRepository;
import com.santanderbr.contas.services.ClientService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientServiceImpl implements ClientService {
	@Autowired ClientRepository clientRepository;
	@Autowired ClientMapper clientMapper;
	
	@Override
	public ResponseEntity<ClientResponseDTO> getClient(Long id) {
		ClientResponseDTO response = new ClientResponseDTO();
		Optional<Client> client = clientRepository.findById(id);
		
		if(client.isEmpty()) {
			response.setMessage("Client not found");
			return ResponseEntity.status(422).body(response);
		} 
		
		response = clientMapper.toResponseDto(client.get());
		return ResponseEntity.status(200).body(response);
	}
}
