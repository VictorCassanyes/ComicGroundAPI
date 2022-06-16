package com.proyecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//Permisos necesarios para crear token y comprobarlo
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//Guardar en memoria un cliente que va a ser mi aplicación, y definir como van a ser los tokens
		clients.inMemory().withClient(ConstantesSeguridad.NOMBRE_CLIENTE).secret("{noop}"+ConstantesSeguridad.CLAVE_CLIENTE)
		.authorizedGrantTypes("password", "refresh_token")
		.scopes("write", "read")
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
		//Utilizar clave mac propia como firma para mayor seguridad
		jwtAccessTokenConverter.setSigningKey(ConstantesSeguridad.CLAVE_MAC);
		return jwtAccessTokenConverter;
	}
	
}
