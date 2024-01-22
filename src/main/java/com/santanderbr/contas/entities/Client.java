package com.santanderbr.contas.entities;



import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_clients")
public class Client {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String document;
	private String type;
	private String password;
	private Integer status;
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_address")
	private Address address;
	
    public Boolean validateAddress() {
    	return (address.getStatus().equals(0)) ? true : false;
    }
}
