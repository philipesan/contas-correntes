package com.santanderbr.contas.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santanderbr.contas.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findById(Long id);
	Optional<Address> findByZipCode(String zipCode);
	Page<Address> findAll(Pageable page);
	Page<Address> findAllByStatus(Integer status, Pageable page);

}
