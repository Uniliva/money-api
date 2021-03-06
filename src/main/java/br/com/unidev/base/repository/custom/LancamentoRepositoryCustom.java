package br.com.unidev.base.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;
import br.com.unidev.base.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryCustom {
    Page<Lancamento> buscarPorFiltro(LancamentoFiltro filtro, Pageable pagina);
	public Page<ResumoLancamento> resumir(LancamentoFiltro lancamentoFilter, Pageable pageable);
  
}