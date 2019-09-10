package br.com.unidev.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unidev.base.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Integer>{

}
