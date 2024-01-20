package com.santanderbr.contas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_operations")

public class Operation {
	@Id
	@GeneratedValue
	private Long id;
	private Integer operationLine;
    private BigDecimal amount;
    private Boolean notificationFlag;
    private LocalDate notificationDate;
    
    @OneToOne
    @JoinColumn(columnDefinition="integer", name = "fk_client")
	private Client client;

    


}