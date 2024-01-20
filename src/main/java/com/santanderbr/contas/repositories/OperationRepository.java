package com.santanderbr.contas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>  {

}
