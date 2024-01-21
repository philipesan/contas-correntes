package com.santanderbr.contas.dto.responses;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.santanderbr.contas.entities.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseDTO {
	private Long id;
	private String branch;
	private BigDecimal balance;
	private Integer status;
	private ClientResponseDTO accountHolder;
}
