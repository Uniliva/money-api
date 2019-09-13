package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.unidev.base.model.Categoria;
import br.com.unidev.base.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> buscarTodos() {
		return repo.findAll();
	}
	
	public Categoria salvar(Categoria categoria) {
		return repo.save(categoria);
	}

	public Categoria buscaPorCodigo(Integer codigo) {
		return repo.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Categoria atualizar(Integer codigo, Categoria categoria) {
		Categoria categoriaSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		return repo.save(categoriaSalva);
	}

}