package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entity.Client;
import com.santanderbr.contas.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	Optional<Transaction> findById(Long id);
	List<Transaction> findByClient(Client client);

}
