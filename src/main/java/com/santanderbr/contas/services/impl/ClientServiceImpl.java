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
import com.santanderbr.contas.dto.requests.ClientRequestDTO;
import com.santanderbr.contas.dto.responses.AddressResponseDTO;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.dto.responses.ClientResponseDTO;
import com.santanderbr.contas.entities.Address;
import com.santanderbr.contas.entities.Client;
import com.santanderbr.contas.mapstruct.ClientMapper;
import com.santanderbr.contas.repositories.AddressRepository;
import com.santanderbr.contas.repositories.ClientRepository;
import com.santanderbr.contas.services.ClientService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientServiceImpl implements ClientService {
	@Autowired ClientRepository clientRepository;
	@Autowired ClientMapper clientMapper;
	@Autowired AddressRepository addressRepository;
	
	@Override
	public ResponseEntity<ApiResponseDTO> listClient(Integer page, Integer items, String sort,
			String direction, Integer status) {
		
		
		Pageable pageable;
		if (direction.equals("0")) {
			pageable = PageRequest.of(page, items, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, items, Sort.by(sort).descending());
		}
		
		Page<ClientResponseDTO> search;
		
		if(status.equals(3)) {
			search = clientRepository.findAll(pageable).map(clientMapper::toResponseDto);
		} else {
			search = clientRepository.findAllByStatus(status, pageable).map(clientMapper::toResponseDto);			
		}
		
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		apiResponse.setMessage("Listing Results");
		apiResponse.setContent(search);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@Override
	public ResponseEntity<ApiResponseDTO> getClient(String param, String value) {
		ClientResponseDTO response = new ClientResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		Optional<Client> client;
		
		if(param.equals("id")) {
			Long id = Long.valueOf(value);
			client = clientRepository.findById(id);
		} else if(param.equals("doc")) {
			client = clientRepository.findByDocument(value);
		} else {
			apiResponse.setMessage("Invalid request Parameter: " + param);
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		if(client.isEmpty()) {
			apiResponse.setMessage("Client not found");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		response = clientMapper.toResponseDto(client.get());
		apiResponse.setMessage("Client found!");
		apiResponse.setContent(response);
		return ResponseEntity.status(200).body(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponseDTO> createClient(ClientRequestDTO client) {
		ClientResponseDTO response = new ClientResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		
		log.warn("Registering client: "+ client.getName());
		
		Optional<Address> address = this.locateAddress(client.getAddressId());
		
		if(address.isEmpty()) {
			log.warn("Address: " + client.getAddressId() + " not found");
			apiResponse.setMessage("Address could not be found");
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		log.warn("Checking if already exists: "+ client.getName());
		
		if(this.alreadyCreated(client.getDocument())) {
			log.warn("Client: " + client.getDocument() + " Already exists");
			apiResponse.setMessage("Client already exists");
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		client.setDocument(this.documentFormat(client.getDocument()));
		if (client.getDocument().isEmpty()) {
			log.error("Invalid Document value");
			apiResponse.setMessage("Invalid Document Value");
			return ResponseEntity.status(422).body(apiResponse);
		}
		
		Client newClient = clientMapper.toEntity(client);
		newClient.setStatus(0);
		newClient.setAddress(address.get());
		
		try {
			newClient = clientRepository.save(newClient);
			response = clientMapper.toResponseDto(newClient);
			apiResponse.setMessage("Client created successfully!");
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
	public ResponseEntity<ApiResponseDTO> updateClient(ClientRequestDTO client, Long idClient) {
		ClientResponseDTO response = new ClientResponseDTO();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		
		client.setDocument(documentFormat(client.getDocument()));

		if (client.getDocument().isEmpty()) {
			apiResponse.setMessage("Invalid Document");
			log.warn("Invalid Document");
			return ResponseEntity.status(422).body(apiResponse);
		} 
		
		Optional<Address> address = this.locateAddress(client.getAddressId());
		
		if(address.isEmpty()) {
			log.warn("Address: " + client.getAddressId() + " not found");
			apiResponse.setMessage("Address could not be found");
			return ResponseEntity.status(422).body(apiResponse);
		}
				
		Client newClient = clientMapper.toEntity(client);
		newClient.setId(idClient);
		newClient.setAddress(address.get());
	
		try {
			newClient = clientRepository.save(newClient);
			response = clientMapper.toResponseDto(newClient);
			apiResponse.setMessage("Client Updated!");
			apiResponse.setContent(response);
			log.info("Entry updated: " + response.getId());
			return ResponseEntity.status(201).body(apiResponse);
		} catch(Exception e) {
			apiResponse.setMessage(e.getMessage());
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(apiResponse);
		}
	}
	
	
	private Optional<Address> locateAddress(Long addressId) {
		Optional<Address> address = addressRepository.findById(addressId);
		return address;
	}
	
	private Boolean alreadyCreated(String document) {
		Optional<Client> client = clientRepository.findByDocument(document);
		if (client.isEmpty()) return false;
		return true;
	}
	
	private String documentFormat(String document) {
		return document.replaceAll("[^0-9]", "");

	}
}
