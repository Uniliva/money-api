package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.unidev.base.model.Pessoa;
import br.com.unidev.base.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;

	public List<Pessoa> buscarTodos() {
		return repo.findAll();
	}

	public Pessoa salvar(Pessoa pessoa) {
		return repo.save(pessoa);
	}

	public Pessoa buscaPorCodigo(Integer codigo) {
		return repo.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Pessoa atualizar(Integer codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repo.save(pessoaSalva);
	}

	public void atualizarStatus(Integer codigo, Boolean status) {
		Pessoa pessoaSalva = this.buscaPorCodigo(codigo);
		pessoaSalva.setAtivo(status);
		repo.save(pessoaSalva);
	}
}
