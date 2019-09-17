package br.com.unidev.base.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.unidev.base.config.Messages;
import br.com.unidev.base.exception.ResourceNotFoundException;
import br.com.unidev.base.exception.RequestInvalidException;

@ControllerAdvice
public class ExceptionCustomHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private Messages msg;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String msgUsuario = msg.getMessage("requisicao.invalida");
		String msgDev = ex.getCause() != null ? ex.getCause().getMessage() : ex.toString();

		Erro erro = new Erro(msgUsuario, msgDev, null);

		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// lista de erros
		List<String> erros = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			erros.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			erros.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		String msgUsuario = msg.getMessage("mensagem.erro.validacao");
		String msgDev = ex.getMessage();

		Erro erro = new Erro(msgUsuario, msgDev, erros);

		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	@ExceptionHandler({ RequestInvalidException.class })
	protected ResponseEntity<Object> handleRequisicaoInvalidaException(RequestInvalidException ex,
			WebRequest request) {

		String msgUsuario = ex.getMessage();
		String msgDev = ExceptionUtils.getRootCauseMessage(ex.getExceptioLancada());

		Erro erro = new Erro(msgUsuario, msgDev, null);

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleRecursoNaoEncontradoException(ResourceNotFoundException ex,
			WebRequest request) {

		String msgUsuario = ex.getMessage();
		String msgDev = ExceptionUtils.getRootCauseMessage(ex);

		Erro erro = new Erro(msgUsuario, msgDev, null);

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}
