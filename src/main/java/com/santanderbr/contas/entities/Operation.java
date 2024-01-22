package com.santanderbr.contas.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "tb_operations", uniqueConstraints = { 
		   @UniqueConstraint(name = "UniqueIdLine",
		   			 columnNames = { "id", "operationLine" }) })

public class Operation {
	@Id
	@GeneratedValue
	private Long id;
	private Integer operationLine;
	private LocalDate operationDate;
    private BigDecimal amount;
    private Boolean notificationFlag;
    private LocalDate notificationDate;
    private Integer status;
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_account")
	private Account account;

    


}
