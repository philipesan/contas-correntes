 package com.santanderbr.contas.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
