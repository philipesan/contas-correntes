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
public class AccountRequestDTO {
	private Long id;
	@NotEmpty(message = "Branch cannot be Empty")
	@NotBlank(message = "Branch cannot be Blank")
	private String branch;
	private Integer status;
	@NotNull(message = "Account Holder cannot be Null")
	private Long accountHolderId;
}
