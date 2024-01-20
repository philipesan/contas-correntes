package com.santanderbr.contas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entity.Address;
import com.santanderbr.contas.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findById(Long id);
	List<Client> findByAddress(Address address);
	Optional<Client> findByName(String name);
}
