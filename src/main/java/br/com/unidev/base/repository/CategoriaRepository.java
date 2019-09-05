package br.com.unidev.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unidev.base.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Integer>{

}
