package com.santanderbr.contas.entities;





import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private Long id;
	private String zipCode;
	private String streetName;
	private String number;
	private String city;
	private String country;
	private String neighborhood;
	private String stateSymbol;
	private String complement;
	private Integer status;
	
}
