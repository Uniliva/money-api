package br.com.unidev.base.exception;

import lombok.Getter;

@Getter
public class RequisicaoInvalidaException extends Exception {
	
    private static final long serialVersionUID = 1L;
    
    private Exception exceptioLancada = this;

    public RequisicaoInvalidaException(String msg) {
        super(msg);
    }

    public RequisicaoInvalidaException(String msg, Exception e) {
        super(msg + " " + e.getMessage());
    	this.exceptioLancada = e;
    }
}
