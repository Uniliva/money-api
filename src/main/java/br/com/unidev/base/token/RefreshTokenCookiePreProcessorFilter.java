package br.com.unidev.base.token;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Filtro responsável por recuperar token enviado pelo browser no cookie 
 * e adicionar token na requisição que gera um novo token.
 *
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {
	
	private static final String OAUTH_TOKEN = "/oauth/token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String REFRESHTOKEN = "refreshToken";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		
		if(OAUTH_TOKEN.equalsIgnoreCase(req.getRequestURI())
				&& REFRESH_TOKEN.equals(req.getParameter(GRANT_TYPE))
				&& req.getCookies() != null) {			
					
			String refreshToken = Stream.of(req.getCookies())
					.filter(cookie -> REFRESHTOKEN.equals(cookie.getName()))
					.findFirst()
					.map(cookie -> cookie.getValue())
					.orElse(null);

			req = new MyServletRequestWrapper(req, refreshToken);
			
		}
		
		chain.doFilter(req, response);
	}
	
	/**
	 * Classe responsável por gerar um novo HttpServletRequest, pois no antigo 
	 * não e permitido adicionar o token no mapa de paramentros.
	 * @author uni
	 *
	 */
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private static final String REFRESH_TOKEN = "refresh_token";

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			final ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put(REFRESH_TOKEN, new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}
	}

}
