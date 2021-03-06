package com.proyecto.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ConfiguracionServidorRecursos extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Permitir cualquier petición a estos endpoints
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/comicground").permitAll()
		//En este endpoint compruebo yo manualmente que vengan de la aplicación cliente que deberían (Mi app)
		.antMatchers(HttpMethod.POST, "/comicground/usuarios/registro").permitAll()
		//Necesario autenticarse para el resto de endpoints
		.anyRequest().authenticated();
		
		//Aquí se incluirían los roles necesarios si tuviese también administradores
	}
}
