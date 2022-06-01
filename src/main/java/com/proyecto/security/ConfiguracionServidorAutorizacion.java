package com.proyecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class ConfiguracionServidorAutorizacion extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		//Permisos necesarios según el endpoint
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		//Guardar en memoria, identificar aplicación e identificar al usuario
		clients.inMemory().withClient("comicgroundapp").secret(passwordEncoder.encode("contraseña123"))
		.scopes("read", "write").authorizedGrantTypes("password", "refresh_token")
		//Tiempo de validez del token y tiempo para su actualización
		.accessTokenValiditySeconds(86400).refreshTokenValiditySeconds(86400);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		//Proceso del endpoint de autenticación y validación y entrega del token
		endpoints.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter());
	}
	
	//Almacena los datos de autenticación del usuario y la información extra que se quiera guardar
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
		return jwtAccessTokenConverter;
	}
	
}
