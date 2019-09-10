package br.com.unidev.base.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent{

	
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private Integer codigo;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer codigo) {
		super(source);
		this.codigo = codigo;
		this.response = response;
	}
}
