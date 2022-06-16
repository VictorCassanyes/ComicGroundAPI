package com.proyecto.services.interfaces;

import java.util.List;

import com.proyecto.models.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public Usuario findById(Integer id);
	
	public Usuario save(Usuario usuario);
	
	public Usuario findByNombreDeUsuario(String nombreDeUsuario);

	public Usuario findByCorreo(String correo);
	
	public boolean update(Usuario usuarioExistente, Usuario usuarioNuevosDatos);

	public boolean checkAuth(String authorization);

	public boolean checkNombreDeUsuario(String nombreDeUsuario);
	
	public boolean checkCorreo(String correo);
	
}
