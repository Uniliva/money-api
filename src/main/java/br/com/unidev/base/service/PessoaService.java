package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unidev.base.config.Messages;
import br.com.unidev.base.exception.RecursoNaoEncontradoException;
import br.com.unidev.base.model.Pessoa;
import br.com.unidev.base.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;
	
	@Autowired
	private Messages msg;

	public List<Pessoa> buscarTodos() {
		return repo.findAll();
	}

	public Pessoa salvar(Pessoa pessoa) {
		return repo.save(pessoa);
	}

	public Pessoa buscaPorCodigo(Integer codigo)  throws RecursoNaoEncontradoException{
		return repo.findById(codigo).orElseThrow(() -> new RecursoNaoEncontradoException(msg.getMessage("recurso.nao.encontrado", "Pessoa")));
	}
	

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Pessoa atualizar(Integer codigo, Pessoa pessoa) throws RecursoNaoEncontradoException {
		Pessoa pessoaSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repo.save(pessoaSalva);
	}

	public void atualizarStatus(Integer codigo, Boolean status) throws RecursoNaoEncontradoException {
		Pessoa pessoaSalva = this.buscaPorCodigo(codigo);
		pessoaSalva.setAtivo(status);
		repo.save(pessoaSalva);
	}
}
