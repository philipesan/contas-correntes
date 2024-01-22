package com.santanderbr.contas.dto.responses;

import java.time.LocalDate;
import java.util.List;

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
public class TransactionResponseDTO {

	private Long id;
    private LocalDate transactionDate;
	private AccountResponseDTO account;
    private List<OperationResponseDTO> operations;


}
