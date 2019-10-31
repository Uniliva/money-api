package br.com.unidev.base.repository.custom;

import java.util.List;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;

public interface LancamentoRepositoryCustom {
  List<Lancamento> buscarPorFiltro(LancamentoFiltro filtro);
  
}