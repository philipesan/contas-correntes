package com.santanderbr.contas.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.santanderbr.contas.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO {
	private String message;
	private Long id;
	private String name;
	private Address address;
}
