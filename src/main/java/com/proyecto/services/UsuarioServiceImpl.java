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

	//Obtener usuario por su id
	@Override
	@Transactional(readOnly=true)
	public Usuario findById(Integer id) {
		//Devolver usuario o si no encuentra nulo
		return usuarioRepository.findById(id).orElse(null);
		
	}

	//Guarda un nuevo usuario en la base de datos
	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	//Comprobar si ya existe un usuario con ese nombre de usuario
	@Override
	@Transactional(readOnly=true)
	public boolean checkNombreDeUsuario(Usuario usuario) {
		
		Usuario usuarioExistente=findByNombreDeUsuario(usuario.getNombreDeUsuario());

		//Si no encuentra un usuario
		if(usuarioExistente==null) {
			return false;
		}
		
		//Si tiene un id y es igual al del usuario encontrado
		if(usuario.getId()!=null && usuarioExistente.getId()==usuario.getId()) {
			return false;
		}
		
		return true;
	}
	
	//Comprobar si ya existe un usuario con ese correo electrónico
	@Override
	@Transactional(readOnly=true)
	public boolean checkCorreo(Usuario usuario) {
		
		Usuario usuarioExistente=findByCorreo(usuario.getCorreo());
		if(usuarioExistente==null) {
			return false;
		}
		return true;
	}
	
	//Actualizar un usuario, solo se actualizará si este ha sido modificado
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
		
		if(!usuarioExistente.getNombreDeUsuario().equals(usuarioNuevosDatos.getNombreDeUsuario())) {
			usuarioExistente.setNombreDeUsuario(usuarioNuevosDatos.getNombreDeUsuario());
			haSidoModificado=true;
		}

		//Si ha sido modificado algun campo este se actualiza y se guardan los cambios
		if(haSidoModificado) {
			usuarioRepository.save(usuarioExistente);
		}
		
		return haSidoModificado;
	}

	//Comprobar credenciales del cliente de forma manual (si no es mi aplicación no se le permite acceder)
	@Override
	@Transactional(readOnly=true)
	public boolean checkAuth(String authorization) {
		//Si es nulo
		if(authorization==null) {
			return false;
		}
		//Dividir credenciales, decodificar y comparar
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
	
	//Obtener objeto 'UserDetails' con algunos datos y los roles del usuario (esto lo utiliza Spring Security)
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
		Usuario usuario=usuarioRepository.findByNombreDeUsuario(nombreDeUsuario);
		
		//Si no existe el usuario
		if(usuario==null) {
			throw new UsernameNotFoundException("Error, no existe ese usuario");
		}
		
		//Asignar un rol por defecto, igual para todos los usuarios (si tuviera administradores sería distinto)
		List<GrantedAuthority> roles=new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("USUARIO"));
		
		return new User(usuario.getNombreDeUsuario(), usuario.getContrasena(), usuario.isHabilitado(), true, true, true, roles);
	}

	//Encontrar usuario por el nombre de usuario
	@Override
	@Transactional(readOnly=true)
	public Usuario findByNombreDeUsuario(String nombreDeUsuario) {
		return usuarioRepository.findByNombreDeUsuario(nombreDeUsuario);
	}
	
	//Encontrar usuario por el correo electrónico
	@Override
	@Transactional(readOnly=true)
	public Usuario findByCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}
}
