package br.com.unidev.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Api de controle de versão")
public class InfoController {
	
	@Value("${app.version}")
	private String version;
	
	@Value("${app.last.update}")
	private String dataAtualizacao;

	@GetMapping("/info")
	@ApiOperation(value = "Retorna informações da aplicação")
	public ResponseEntity<String> info() {
		String info = "MONEY API  - Versão: "+this.version+" - Ultima atualização: "+dataAtualizacao;
		return ResponseEntity.ok(info);
	}
}
