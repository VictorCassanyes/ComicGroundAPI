package com.proyecto.services.interfaces;

import java.util.List;

import com.proyecto.models.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public Usuario findById(Integer id);
	
	public Usuario save(Usuario usuario);
	
	public boolean update(Usuario usuarioExistente, Usuario usuarioNuevosDatos);
	
}
