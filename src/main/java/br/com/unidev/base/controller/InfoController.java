package br.com.unidev.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
	
	@Value("${app.version}")
	private String version;
	
	@Value("${app.last.update}")
	private String dataAtualizacao;

	@GetMapping("/info")
	public ResponseEntity<String> info() {
		String info = "MONEY API  - Versão: "+this.version+" - Ultima atualização: "+dataAtualizacao;
		return ResponseEntity.ok(info);
	}
}
