package com.santanderbr.contas.dto.requests;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TransactionRequestDTO {

	private Long id;
	@NotNull(message = "Transaction Date cannot be Empty")
    private LocalDate transactionDate;
	@NotNull(message = "Account ID cannot be Empty")
	private Long accountId;
	@NotEmpty(message = "No Operations")
    private List<OperationRequestDTO> operations;


}
