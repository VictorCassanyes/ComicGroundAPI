package com.proyecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;

	//@Autowired por la inyección del componente: "AuthenticationManagerBuilder"
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * Registrar el usuario desde su Service y añadir el passwordEncoder por defecto (texto plano)
		 * aunque esté deprecated ya que eso da igual, puesto que voy a manejar la encriptación de 
		 * contraseñas por mi propia cuenta utilizando AES
		 */
		auth.userDetailsService(this.usuarioService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	//Devolver objeto AuthenticationManager, @Bean para inyectar el método en la clase "ConfiguracionServidorAutorizacion"
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Cualquier petición tiene que ser de un usuario autentificado
		http.authorizeRequests().anyRequest().authenticated()
		
		//Quitar la seguridad csrf porque no es necesaria (ya que el 'frontend' está en la aplicación móvil)
		.and().csrf().disable()
		
		//Quitar las sesiones ya que la información del usuario no se guarda en una sesión si no en un token
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
