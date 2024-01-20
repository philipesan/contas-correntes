package com.santanderbr.contas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santanderbr.contas.dto.responses.ClientResponseDTO;

@Service
public interface ClientService {
	ResponseEntity<ClientResponseDTO> getClient(Long idClient);
}
