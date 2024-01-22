package com.santanderbr.contas.entities;

import java.math.BigDecimal;




import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "tb_account",
	   uniqueConstraints = { 
			   @UniqueConstraint(name = "UniqueIdBranch",
					   			 columnNames = { "id", "branch" }) })
public class Account {
	@Id
	@GeneratedValue
	private Long id;
	private String branch;
	private BigDecimal balance;
	private Integer status;
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_client")
	private Client accountHolder;
    
    public Boolean hasBalance(BigDecimal amount) {
    	return (balance.add(amount).compareTo(BigDecimal.ZERO) == -1) ? false : true;
    }
    
    public BigDecimal adjustBalance(BigDecimal amount) {
    	this.balance = balance.add(amount);
    	return balance;
    }
}


