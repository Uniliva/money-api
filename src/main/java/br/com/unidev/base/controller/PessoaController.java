package br.com.unidev.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unidev.base.event.RecursoCriadoEvent;
import br.com.unidev.base.exception.ResourceNotFoundException;
import br.com.unidev.base.model.Pessoa;
import br.com.unidev.base.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_PESQUISAR_PESSOA')")
	public List<Pessoa> listar() {
		return service.buscarTodos();
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = service.salvar(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity<Pessoa> buscaPorCodigo(@PathVariable Integer codigo) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.buscaPorCodigo(codigo));
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_REMOVER_PESSOA')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagarPorCodigo(@PathVariable Integer codigo) {
		service.remove(codigo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_ATUALIZAR_PESSOA')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Pessoa pessoa) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.atualizar(codigo, pessoa));
	}
	
	@PutMapping("/{codigo}/ativo")
	@PreAuthorize("hasRole('ROLE_ATUALIZAR_PESSOA')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Integer codigo, @RequestParam Boolean status) throws ResourceNotFoundException {
		service.atualizarStatus(codigo, status);
	}

}
