package com.santanderbr.contas.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponseDTO {
	private Long id;
	private String zipCode;
	private String streetName;
	private String number;
	private String city;
	private String country;
	private String neighborhood;
	private String stateSymbol;
	private String complement;
	private Integer status;
}
