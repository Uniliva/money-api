package br.com.unidev.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import br.com.unidev.base.service.CustomUserDatailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizarionServerConfig extends AuthorizationServerConfigurerAdapter {
	

	private static final int ACCESS_TOKEN_VALIDITY_IN_SECONDS = 5;// 60 * 60 * 24;
	private static final int REFRESH_TOKEN_VALIDITY_IN_SECONDS = 5;// 60 * 60 * 24 * 30;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDatailsService customUserDatailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAccessTokenConverter tokenConvert;
	
	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret(passwordEncoder.encode("@ngul@r0"))
			.authorizedGrantTypes("password", "refresh_token")
			.scopes("read", "write")
	        .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_IN_SECONDS)  
	        .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_IN_SECONDS);  

		
	}
	
	@Override  
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {  
	    security.tokenKeyAccess("permitAll()")  
	            .checkTokenAccess("isAuthenticated()");  
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore)
			.accessTokenConverter(tokenConvert)
			.reuseRefreshTokens(false)
			.userDetailsService(customUserDatailsService)
			.authenticationManager(authenticationManager);
	}


}
