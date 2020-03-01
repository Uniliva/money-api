package br.com.unidev.base.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unidev.base.event.RecursoCriadoEvent;
import br.com.unidev.base.exception.RequestInvalidException;
import br.com.unidev.base.exception.ResourceNotFoundException;
import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;
import br.com.unidev.base.repository.projection.ResumoLancamento;
import br.com.unidev.base.service.LancamentoService;

@RestController
public class LancamentoController {

	@Autowired
	private LancamentoService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<Lancamento> pesquisar(LancamentoFiltro filtro , Pageable pagina) {
		return service.buscarComFiltros(filtro, pagina);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasRole('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<ResumoLancamento> resumo(LancamentoFiltro filtro , Pageable pagina) {
		return service.resumo(filtro, pagina);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) throws RequestInvalidException {

		Lancamento LancamentoSalva = service.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, LancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(LancamentoSalva);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_PESQUISAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> buscaPorCodigo(@PathVariable Integer codigo) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.buscaPorCodigo(codigo));
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_REMOVER_LANCAMENTO')")
	public void apagarPorCodigo(@PathVariable Integer codigo) {
		service.remove(codigo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_ATUALIZAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Integer codigo,
			@Valid @RequestBody Lancamento lancamento) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.atualizar(codigo, lancamento));
	}

}
