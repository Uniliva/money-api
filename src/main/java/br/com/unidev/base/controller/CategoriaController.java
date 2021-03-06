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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unidev.base.event.RecursoCriadoEvent;
import br.com.unidev.base.exception.ResourceNotFoundException;
import br.com.unidev.base.model.Categoria;
import br.com.unidev.base.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_PESQUISAR_CATEGORIA')")
	public List<Categoria> listar() {
		return service.buscarTodos();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = service.salvar(categoria);	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<Categoria> buscaPorCodigo(@PathVariable Integer codigo) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.buscaPorCodigo(codigo));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_REMOVER_CATEGORIA')")
	public void apagarPorCodigo(@PathVariable Integer codigo) {
		service.remove(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasRole('ROLE_ATUALIZAR_CATEGORIA')")
	public ResponseEntity<Categoria> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Categoria categoria) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.atualizar(codigo, categoria));
	}

}
