package br.com.unidev.base.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "permissao")
@Data
public class Permissao {
	@Id
	private Long codigo;
	private String descricao;
}
