package com.santanderbr.contas.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AddressRequestDTO {
	
	private Long id;
	@NotEmpty(message = "Zip Code cannot be Empty")
	@NotBlank(message = "Zip Code cannot be Blank")
	private String zipCode;
	@NotEmpty(message = "Street Name cannot be Empty")
	@NotBlank(message = "Street Name cannot be Blank")
	private String streetName;
	@NotEmpty(message = "Number cannot be Empty")
	@NotBlank(message = "Number cannot be Blank")
	private String number;
	@NotEmpty(message = "City cannot be Empty")
	@NotBlank(message = "City cannot be Blank")
	private String city;
	@NotEmpty(message = "Country cannot be Empty")
	@NotBlank(message = "SCountry cannot be Blank")
	private String country;
	@NotEmpty(message = "Neighborhood cannot be Empty")
	@NotBlank(message = "Neighborhood cannot be Blank")
	private String neighborhood;
	@NotEmpty(message = "State Symbol cannot be Empty")
	@NotBlank(message = "State Symbol cannot be Blank")
    @Size(max = 2, message 
    = "Invalid State Symbol")
	private String stateSymbol;
	private String complement;
	private Integer status;
}
