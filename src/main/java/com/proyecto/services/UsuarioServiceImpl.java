package com.proyecto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.models.Usuario;
import com.proyecto.repositories.UsuarioRepository;
import com.proyecto.services.interfaces.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

	private Logger logger=LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		//Devuelve todos los usuarios en la base de datos
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findById(Integer id) {
		//Devuelve el usuario con ese id, si no existe devuelve null
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		//Guarda un usuario en la base de datos (o actualiza uno ya existente)
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public boolean update(Usuario usuarioExistente, Usuario usuarioNuevosDatos) {
		//Si ha sido modificado algun campo este se actualiza y se guarda el usuario
		boolean haSidoModificado=false;
		
		if(!usuarioExistente.getNombre().equals(usuarioNuevosDatos.getNombre())) {
			usuarioExistente.setNombre(usuarioNuevosDatos.getNombre());
			haSidoModificado=true;
		}
		
		if(!usuarioExistente.getApellidos().equals(usuarioNuevosDatos.getApellidos())) {
			usuarioExistente.setApellidos(usuarioNuevosDatos.getApellidos());
			haSidoModificado=true;
		}
		
		if(!usuarioExistente.getCorreo().equals(usuarioNuevosDatos.getCorreo())) {
			usuarioExistente.setCorreo(usuarioNuevosDatos.getCorreo());
			haSidoModificado=true;
		}
		
		if(!usuarioExistente.getNombreDeUsuario().equals(usuarioNuevosDatos.getNombreDeUsuario())) {
			usuarioExistente.setNombreDeUsuario(usuarioNuevosDatos.getNombreDeUsuario());
			haSidoModificado=true;
		}
		
		if(!usuarioExistente.getContraseña().equals(usuarioNuevosDatos.getContraseña())) {
			usuarioExistente.setContraseña(usuarioNuevosDatos.getContraseña());
			haSidoModificado=true;
		}
		
		if(haSidoModificado) {
			usuarioRepository.save(usuarioExistente);
		}
		
		return haSidoModificado;
	}

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
		Usuario usuario=usuarioRepository.findByNombreDeUsuario(nombreDeUsuario);
		
		if(usuario==null) {
			logger.error("Error, no existe ese usuario");
			throw new UsernameNotFoundException("Error, no existe ese usuario");
		}
		
		List<GrantedAuthority> roles=usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.collect(Collectors.toList());
		
		return new User(usuario.getNombreDeUsuario(), usuario.getContraseña(), usuario.isHabilitado(), true, true, true, roles);
	}
}
