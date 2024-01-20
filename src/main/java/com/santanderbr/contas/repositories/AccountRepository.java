package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entity.Account;
import com.santanderbr.contas.entity.Client;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findById(Long id);
	List<Account> findByClient(Client client);
	
}