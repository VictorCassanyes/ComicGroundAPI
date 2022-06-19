package com.proyecto.services.interfaces;

import com.proyecto.models.Usuario;

public interface IUsuarioService {
	
	public Usuario findById(Integer id);
	
	public Usuario save(Usuario usuario);
	
	public Usuario findByNombreDeUsuario(String nombreDeUsuario);

	public Usuario findByCorreo(String correo);
	
	public boolean update(Usuario usuarioExistente, Usuario usuarioNuevosDatos);

	public boolean checkAuth(String authorization);

	public boolean checkNombreDeUsuario(Usuario usuario);
	
	public boolean checkCorreo(Usuario usuario);
	
}
