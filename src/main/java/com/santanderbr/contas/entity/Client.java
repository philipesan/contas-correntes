package com.santanderbr.contas.entity;

import java.math.BigDecimal;

import javax.persistence.*;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
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
	private Integer id;
	private String password;
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_address")
	private Address address;
	
}
