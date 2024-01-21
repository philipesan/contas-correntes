package com.santanderbr.contas.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ClientRequestDTO {
	private Long id;
	@NotEmpty(message = "Name cannot be Empty")
	@NotBlank(message = "Name cannot be Blank")
	private String name;
	@NotNull(message = "Address ID be Blank")
	private Long addressId;
	@NotEmpty(message = "Document cannot be Empty")
	@NotBlank(message = "Document cannot be Blank")
	private String document;
	@NotEmpty(message = "Type cannot be Empty")
	@NotBlank(message = "Type cannot ID be Blank")
	private String type;
	@NotEmpty(message = "Password cannot be Empty")
	@NotBlank(message = "Password cannot ID be Blank")
	private String password;
	private String status;
}
