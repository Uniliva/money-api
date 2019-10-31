package br.com.unidev.base.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFiltro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String descricao;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataLancamentoDe;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataLancamentoAte;

}
