package com.santanderbr.contas.dto.requests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
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
public class OperationRequestDTO {

	@NotNull(message = "Amount cannot be Blank")
    private BigDecimal amount;
	@NotNull(message = "Notification Flag cannot be Blank")
    private Boolean notificationFlag;
	@NotNull(message = "Operation Line cannot be Blank")
    private Integer operationLine;
	@NotNull(message = "Operation Date cannot be Empty")
    private LocalDate operationDate;
	@NotNull(message = "Account ID cannot be Blank")
	private Long accountId;
}
