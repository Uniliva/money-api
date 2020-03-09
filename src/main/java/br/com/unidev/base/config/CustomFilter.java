package br.com.unidev.base.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomFilter implements Filter {
	
	@Value("${cors.origin.permitida}")
	private String originPermitida;
	
	private static final String OAUTH_TOKEN = "/oauth/token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String REFRESHTOKEN = "refreshToken";
	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	
    	
		
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
       
        response.setHeader("Access-Control-Allow-Origin", originPermitida);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        

        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) && originPermitida.equals(request.getHeader("Origin"))) {
        	response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, authorization");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	
        	// Usado para adicionar o refresh token na requisicao, quando chamado a url de refresh
        	if(request.getRequestURI().contains(OAUTH_TOKEN)
    				&& REFRESH_TOKEN.equals(request.getParameter(GRANT_TYPE))
    				&& request.getCookies() != null) {			
    					
    			String refreshToken = Stream.of(request.getCookies())
    					.filter(cookie -> REFRESHTOKEN.equals(cookie.getName()))
    					.findFirst()
    					.map(cookie -> cookie.getValue())
    					.orElse(null);

    			request = new MyServletRequestWrapper(request, refreshToken);    			
    			
    		}
        	
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
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
			Map<String, String[]> map = new HashMap<>(getRequest().getParameterMap());
			map.put(REFRESH_TOKEN, new String[] { refreshToken });
			return map;
		}
	}
}