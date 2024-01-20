package com.santanderbr.contas.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.santanderbr.contas.dto.requests.AddressRequestDTO;
import com.santanderbr.contas.dto.responses.AddressResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.entities.Address;
import com.santanderbr.contas.mapstruct.AddressMapper;
import com.santanderbr.contas.repositories.AddressRepository;
import com.santanderbr.contas.services.AddressService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AddressServiceImpl implements AddressService {
	@Autowired AddressRepository addressRepository;
	@Autowired AddressMapper addressMapper;
	
	@Override
	public ResponseEntity<ApiResponseDTO> listAddress(Integer page, Integer items, String sort,
			String direction, Integer status) {
		
		
		Pageable pageable;
		if (direction == "0") {
			pageable = PageRequest.of(page, items, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, items, Sort.by(sort).descending());
		}
		
		Page<Address> search;
		
		if(status.equals(3)) {
			search = addressRepository.findAll(pageable);
		} else {
			search = addressRepository.findAllByStatus(status, pageable);			
		}
		
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		apiResponse.setMessage("Listing Results");
		apiResponse.setContent(search);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@Override
	public ResponseEntity<ApiResponseDTO> getAddress(Long idAddress) {
		AddressResponseDTO response = new AddressResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Optional<Address> address = addressRepository.findById(idAddress);
		
		if(address.isEmpty()) {
			apiResponse.setMessage("Address not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		response = addressMapper.toResponseDto(address.get());
		apiResponse.setMessage("Address Found!");
		apiResponse.setContent(address);
		return ResponseEntity.status(200).body(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponseDTO> createAddress(AddressRequestDTO address) {
		AddressResponseDTO response = new AddressResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		address.setZipCode(zipCodeFormat(address.getZipCode()));

		if (address.getZipCode().isEmpty()) {
			apiResponse.setMessage("Invalid Zip Code");
			log.warn("Invalid Zip Code Attempt");
			return ResponseEntity.status(422).body(apiResponse);
		} 

		Optional<Address> addressValidation = addressRepository.findByZipCode(address.getZipCode());
		

		
		if(!addressValidation.isEmpty()) {
			response = addressMapper.toResponseDto(addressValidation.get());
			apiResponse.setMessage("Address already registered");
			apiResponse.setContent(response);
			return ResponseEntity.status(200).body(apiResponse);
		}
		
		Address newAddress = addressMapper.toEntity(address);
		newAddress.setStatus(0);
		try {
			newAddress = addressRepository.save(newAddress);
			response = addressMapper.toResponseDto(newAddress);
			apiResponse.setMessage("Address created successfully!");
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
	public ResponseEntity<ApiResponseDTO> updateAddress(AddressRequestDTO address, Long idAddress) {
		AddressResponseDTO response = new AddressResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		address.setZipCode(zipCodeFormat(address.getZipCode()));

		if (address.getZipCode().isEmpty()) {
			apiResponse.setMessage("Invalid Zip Code");
			log.warn("Invalid Zip Code Attempt");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		Optional<Address> validateAddress = addressRepository.findById(idAddress);

		
		if(validateAddress.isEmpty()) {
			apiResponse.setMessage("Address not found");
			log.info("Address not found " + response.getZipCode());
			return ResponseEntity.status(422).body(apiResponse);
		} 
				
		Address newAddress = addressMapper.toEntity(address);
		newAddress.setId(idAddress);
	
		try {
			newAddress = addressRepository.save(newAddress);
			response = addressMapper.toResponseDto(newAddress);
			apiResponse.setMessage("Address Updated !");
			apiResponse.setContent(response);
			log.info("Entry updated: " + response.getId());
			return ResponseEntity.status(201).body(apiResponse);
		} catch(Exception e) {
			apiResponse.setMessage(e.getMessage());
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}
	}
	
	
	private String zipCodeFormat(String zipCode) {
		return zipCode.replaceAll("[^0-9]", "");

	}

}
