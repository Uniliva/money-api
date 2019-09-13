package br.com.unidev.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unidev.base.event.RecursoCriadoEvent;
import br.com.unidev.base.model.Categoria;
import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.Pessoa;
import br.com.unidev.base.service.CategoriaService;
import br.com.unidev.base.service.LancamentoService;
import br.com.unidev.base.service.PessoaService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService service;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Lancamento> listar() {
		return service.buscarTodos();
	}

	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Categoria categoria = categoriaService.buscaPorCodigo(lancamento.getCategoria().getCodigo());
		lancamento.setCategoria(categoria);

		Pessoa pessoa = pessoaService.buscaPorCodigo(lancamento.getPessoa().getCodigo());
		lancamento.setPessoa(pessoa);

		Lancamento LancamentoSalva = service.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, LancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(LancamentoSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaPorCodigo(@PathVariable Integer codigo) {
		return ResponseEntity.ok(service.buscaPorCodigo(codigo));
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagarPorCodigo(@PathVariable Integer codigo) {
		service.remove(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Integer codigo,
			@Valid @RequestBody Lancamento lancamento) {
		return ResponseEntity.ok(service.atualizar(codigo, lancamento));
	}

}
