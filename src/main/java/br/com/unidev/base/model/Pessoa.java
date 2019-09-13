package br.com.unidev.base.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "pessoa")
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@NotEmpty(message = "{campo.obrigatorio}")
	private String nome;
	
	@Embedded
	private Endereco endereco;
	
	@NotNull(message = "{campo.obrigatorio}")
	private  boolean ativo;

}
