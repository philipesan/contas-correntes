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
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/address")
public class AddressController {
	@Autowired
	AddressService addressService;
	
	@GetMapping(path="/list")
    public ResponseEntity<ApiResponseDTO> listAddresses(@RequestParam Integer page, 
    		@RequestParam Integer items,
    		@RequestParam(defaultValue = "id") String sort,
    		@RequestParam(defaultValue = "0") String direction,
    		@RequestParam(defaultValue = "3") Integer status) {
		return addressService.listAddress(page, items, sort, direction, status);
	}
	
	@GetMapping(path="/search")
	public ResponseEntity<ApiResponseDTO> getAddress(
			@RequestParam(defaultValue = "id") String param,
    		@RequestParam(defaultValue = "0") String value) {
		return addressService.getAddress(param, value);
		
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO address) {
		return addressService.createAddress(address);
	}
	
	@PatchMapping(path="/{idAddress}")
	public ResponseEntity<ApiResponseDTO> updateAddress(@PathVariable Long idAddress, @Valid @RequestBody AddressRequestDTO address) {
		return addressService.updateAddress(address, idAddress);
	}
}
