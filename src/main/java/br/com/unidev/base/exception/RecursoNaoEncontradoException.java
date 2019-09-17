package br.com.unidev.base.exception;

public class RecursoNaoEncontradoException extends Exception {
	
    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoException(String msg) {
        super(msg);
    }
    
    public RecursoNaoEncontradoException(String msg, Exception e) {
        super(msg + " " + e.getMessage());
    }
}
