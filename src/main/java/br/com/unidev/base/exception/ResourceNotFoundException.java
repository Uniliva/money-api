package br.com.unidev.base.exception;

public class ResourceNotFoundException extends Exception {
	
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    
    public ResourceNotFoundException(String msg, Exception e) {
        super(msg + " " + e.getMessage());
    }
}
