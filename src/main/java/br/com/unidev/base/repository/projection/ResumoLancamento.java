package br.com.unidev.base.repository.projection;

import java.time.LocalDate;

import br.com.unidev.base.model.TipoLancamento;
import lombok.Data;

@Data
public class ResumoLancamento {
	
	private Integer codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private Double valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	public ResumoLancamento(Integer codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			Double valor, TipoLancamento tipo, String categoria, String pessoa) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

}
