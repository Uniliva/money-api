package br.com.unidev.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.repository.custom.LancamentoRepositoryCustom;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Integer>, LancamentoRepositoryCustom {

}
