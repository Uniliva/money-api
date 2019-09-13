package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repo;
	
	public List<Lancamento> buscarTodos() {
		List<Lancamento> findAll = repo.findAll();
		return findAll;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		return repo.save(lancamento);
	}

	public Lancamento buscaPorCodigo(Integer codigo) {
		return repo.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Lancamento atualizar(Integer codigo, Lancamento lancamento) {
		Lancamento lancamentoSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return repo.save(lancamentoSalva);
	}

}
