package br.com.unidev.base.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unidev.base.config.Messages;
import br.com.unidev.base.exception.ResourceNotFoundException;
import br.com.unidev.base.exception.BusinessException;
import br.com.unidev.base.exception.RequestInvalidException;
import br.com.unidev.base.model.Categoria;
import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;
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
	
	public List<Lancamento> buscarComFiltros(LancamentoFiltro filtro) {
		List<Lancamento> findAll = repo.buscarPorFiltro(filtro);
		return findAll;
	}
	
	public Lancamento salvar(Lancamento lancamento) throws RequestInvalidException {
		try {
			Pessoa pessoa = pessoaService.buscaPorCodigo(lancamento.getPessoa().getCodigo());
			if(!pessoa.isAtivo()) throw new BusinessException(msg.getMessage("pessoa.inativa"));
			lancamento.setPessoa(pessoa);
			
			
			Categoria categoria = categoriaService.buscaPorCodigo(lancamento.getCategoria().getCodigo());
			lancamento.setCategoria(categoria);
			
		} catch (Exception ex) {
			throw new RequestInvalidException(msg.getMessage("requisicao.invalida"),ex);
		}		

		return repo.save(lancamento);
	}

	public Lancamento buscaPorCodigo(Integer codigo) throws ResourceNotFoundException {
		return repo.findById(codigo).orElseThrow(() -> new ResourceNotFoundException(msg.getMessage("recurso.nao.encontrado", "Lancamento")));
	}

	public void remove(Integer codigo) {
		repo.deleteById(codigo);
	}

	public Lancamento atualizar(Integer codigo, Lancamento lancamento) throws ResourceNotFoundException {
		Lancamento lancamentoSalva = this.buscaPorCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return repo.save(lancamentoSalva);
	}

}
