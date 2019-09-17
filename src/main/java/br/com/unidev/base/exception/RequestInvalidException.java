package br.com.unidev.base.exception;

import lombok.Getter;

@Getter
public class RequestInvalidException extends Exception {
	
    private static final long serialVersionUID = 1L;
    
    private Exception exceptioLancada = this;

    public RequestInvalidException(String msg) {
        super(msg);
    }

    public RequestInvalidException(String msg, Exception e) {
        super(msg + " " + e.getMessage());
    	this.exceptioLancada = e;
    }
}
