 package com.santanderbr.contas.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_transactions")
public class Transaction {
	@Id
	@GeneratedValue
	private Long id;
    private LocalDate transactionDate;
    
    @OneToOne
    @JoinColumn(columnDefinition="integer", name = "fk_client")
	private Client client;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_transac")
    private List<Operation> operations;

    
    
	

}
