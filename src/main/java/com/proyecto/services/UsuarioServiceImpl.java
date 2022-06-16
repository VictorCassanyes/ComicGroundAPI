package com.proyecto.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
import com.proyecto.security.ConstantesSeguridad;
import com.proyecto.services.interfaces.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {
	
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
		//Guarda un usuario en la base de datos
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean checkNombreDeUsuario(String nombreDeUsuario) {
		
		Usuario usuarioExistente=findByNombreDeUsuario(nombreDeUsuario);
		if(usuarioExistente!=null) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean checkCorreo(String correo) {
		
		Usuario usuarioExistente=findByCorreo(correo);
		if(usuarioExistente!=null) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean update(Usuario usuarioExistente, Usuario usuarioNuevosDatos) {
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
		
		if(!usuarioExistente.getContrasena().equals(usuarioNuevosDatos.getContrasena())) {
			usuarioExistente.setContrasena(usuarioNuevosDatos.getContrasena());
			haSidoModificado=true;
		}

		//Si ha sido modificado algun campo este se actualiza y se guarda el usuario
		if(haSidoModificado) {
			usuarioRepository.save(usuarioExistente);
		}
		
		return haSidoModificado;
	}

	//Comprobar credenciales del cliente de forma manual (si no es mi aplicación no se le permite acceder)
	@Override
	@Transactional(readOnly=true)
	public boolean checkAuth(String authorization) {
		
		if(authorization==null) {
			return false;
		}
		
		try {
		String[] credenciales=authorization.split(" ");
		byte[] credencialesDesc=Base64.getDecoder().decode(credenciales[1].getBytes());
		String credencialesDescString=new String(credencialesDesc);
		String[] nombreYContrasena=credencialesDescString.split(":");
		if(!nombreYContrasena[0].equals(ConstantesSeguridad.NOMBRE_CLIENTE) || !nombreYContrasena[1].equals(ConstantesSeguridad.CLAVE_CLIENTE)) {
			return false;
		}
		return true;
		} catch(IndexOutOfBoundsException | IllegalArgumentException e) {
			return false;
		}
	}
	
	//Obtener objeto 'UserDetails' con algunos datos y los roles del usuario
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
		Usuario usuario=usuarioRepository.findByNombreDeUsuario(nombreDeUsuario);
		
		if(usuario==null) {
			throw new UsernameNotFoundException("Error, no existe ese usuario");
		}
		//Asignar un rol por defecto, igual para todos los usuarios
		List<GrantedAuthority> roles=new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("USUARIO"));
		
		return new User(usuario.getNombreDeUsuario(), usuario.getContrasena(), usuario.isHabilitado(), true, true, true, roles);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByNombreDeUsuario(String nombreDeUsuario) {
		return usuarioRepository.findByNombreDeUsuario(nombreDeUsuario);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}
}
