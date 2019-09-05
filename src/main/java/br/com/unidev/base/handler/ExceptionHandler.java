package br.com.unidev.base.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.unidev.base.config.Menssages;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private Menssages msg;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String msgUsuario= msg.getMessage("mensagem.invalida");
		
		Erro erro = new Erro();
		erro.setMsgUsuario(msgUsuario);
		erro.setMsgDesenvolvedor(ex.getCause().getMessage());
		
	
		return handleExceptionInternal(ex, erro, headers, status, request);
	}

}
