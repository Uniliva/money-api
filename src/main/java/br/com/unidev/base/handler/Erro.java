package br.com.unidev.base.handler;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class Erro {
	private String msgUsuario;
	private String msgDesenvolvedor;
	private List<String> erros;
}
