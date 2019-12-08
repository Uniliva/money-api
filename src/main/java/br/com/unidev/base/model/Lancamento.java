package br.com.unidev.base.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name = "data_vencimento")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataPagamento;
	
	@NotNull
	private Double valor;
	
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;
	
	

}
