package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Address;
import com.santanderbr.contas.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findById(Long id);
	Optional<Client> findByDocument(String document);
	List<Client> findByAddress(Address address);
	Optional<Client> findByName(String name);
	Page<Client> findAll(Pageable page);
	Page<Client> findAllByStatus(Integer status, Pageable page);
}
