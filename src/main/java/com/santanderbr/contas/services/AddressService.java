package com.santanderbr.contas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santanderbr.contas.dto.requests.AddressRequestDTO;
import com.santanderbr.contas.dto.responses.AddressResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;

@Service
public interface AddressService {
	ResponseEntity<ApiResponseDTO> createAddress(AddressRequestDTO address);
	ResponseEntity<ApiResponseDTO> updateAddress(AddressRequestDTO address, Long idAddress);
	ResponseEntity<ApiResponseDTO> listAddress(Integer page, Integer items, String sort, String direction, Integer status);
	ResponseEntity<ApiResponseDTO> getAddress(String param, String value);
}
