package com.santanderbr.contas.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.santanderbr.contas.BusinessException;
import com.santanderbr.contas.dto.responses.ApiResponseDTO;
import com.santanderbr.contas.dto.responses.OperationResponseDTO;
import com.santanderbr.contas.entities.Operation;

public interface OperationService {

	ResponseEntity<ApiResponseDTO> getOperation(Long idOperation);
	List<Operation> makeOperations(List<Operation> operations) throws BusinessException;
	ResponseEntity<String> notifyService(Operation operation);
	List<OperationResponseDTO> convertOperations(List<Operation> operations);
	List<Operation> rollbackOperations(List<Operation> operations) throws BusinessException;
}
