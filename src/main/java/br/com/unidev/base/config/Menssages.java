package br.com.unidev.base.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

@Configuration
public class Menssages {

	@Autowired
	private MessageSource message;
	
	final Locale local = LocaleContextHolder.getLocale();
	
	public String getMessage(String key) {		
		return message.getMessage(key, null, local);
	}
	
	public String getMessage(String key, String ...args) {		
		return message.getMessage(key, args, local);
	}
}
