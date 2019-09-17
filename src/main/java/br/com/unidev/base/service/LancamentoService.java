package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unidev.base.config.Messages;
import br.com.unidev.base.exception.RecursoNaoEncontradoException;
import br.com.unidev.base.exception.RequisicaoInvalidaException;
import br.com.unidev.base.model.Categoria;
import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.Pessoa;
import br.com.unidev.base.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repo;	

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private PessoaService pessoaService;
	
	
	@Autowired
	private Messages msg;
	
	public List<Lancamento> buscarTodos() {
		List<Lancamento> findAll = repo.findAll();
		return findAll;
	}
	
	public Lancamento salvar(Lancamento lancamento) throws RequisicaoInvalidaException {
		try {
			Integer codigoCategoria = lancamento.getCategoria().getCodigo();
			Categoria categoria = categoriaService.buscaPorCodigo(codigoCategoria);
			lancamento.setCategoria(categoria);
			
			Integer codigoPessoa = lancamento.getPessoa().getCodigo();
			Pessoa pessoa = pessoaService.buscaPorCodigo(codigoPessoa);
			lancamento.setPessoa(pessoa);


		} catch (Exception ex) {
			throw new RequisicaoInvalidaException(msg.getMessage("requisicao.invalida"),ex);
		}		

		return repo.save(lancamento);
	}

	public Lancamento buscaPorCodigo(Integer codigo) throws RecursoNaoEncontradoException {
		return repo.findById(codigo).orElseThrow(() -> new RecursoNaoEncontradoException(msg.getMessage("recurso.nao.encontrado", "Lancamento")));
	}

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Lancamento atualizar(Integer codigo, Lancamento lancamento) throws RecursoNaoEncontradoException {
		Lancamento lancamentoSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return repo.save(lancamentoSalva);
	}

}
