package com.santanderbr.contas.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

@Entity
@Table(name = "tb_address")
public class Address {
	@Id
	@GeneratedValue
	private Integer id;
	private String zipCode;
	private String streetName;
	private String number;
	private String city;
	private String country;
	
}
