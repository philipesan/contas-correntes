package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Account;
import com.santanderbr.contas.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	Optional<Transaction> findById(Long id);
	List<Transaction> findAllByAccountId(final Long accountId);
}
