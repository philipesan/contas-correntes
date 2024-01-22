package com.santanderbr.contas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>  {
	Optional<Operation> findById(Long id);
}
