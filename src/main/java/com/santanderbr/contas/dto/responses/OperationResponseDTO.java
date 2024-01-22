package com.santanderbr.contas.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class OperationResponseDTO {
	
	private Long id;
	private Integer operationLine;
    private BigDecimal amount;
    private Boolean notificationFlag;
    private LocalDate notificationDate;
    private LocalDate operationDate;
	private AccountResponseDTO account;
    private Integer status;

}
