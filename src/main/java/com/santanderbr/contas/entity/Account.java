package com.santanderbr.contas.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_account")
public class Account {
	@Id
	@GeneratedValue
	private String id;
	private String branch;
	private BigDecimal balance;
	private Boolean status;
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_client")
	private Client accountHolder;
}
