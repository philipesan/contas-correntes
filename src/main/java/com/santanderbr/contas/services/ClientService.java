package com.santanderbr.contas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santanderbr.contas.dto.requests.ClientRequestDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.dto.responses.ClientResponseDTO;

import jakarta.validation.Valid;

@Service
public interface ClientService {
	ResponseEntity<ApiResponseDTO> createClient(ClientRequestDTO client);

	ResponseEntity<ApiResponseDTO> getClient(String param, String value);

	ResponseEntity<ApiResponseDTO> updateClient(ClientRequestDTO client, Long idClient);

	ResponseEntity<ApiResponseDTO> listClient(Integer page, Integer items, String sort, String direction,
			Integer status);
}
