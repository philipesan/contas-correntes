package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Account;
import com.santanderbr.contas.entities.Client;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findById(Long id);
	List<Account> findByAccountHolder(Client client);
	Page<Account> findAllByStatus(Pageable pageable, Integer status);
	
}