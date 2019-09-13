package br.com.unidev.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unidev.base.model.Lancamento;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Integer>{

}
