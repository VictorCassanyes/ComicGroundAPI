package com.proyecto.repositories;


import org.springframework.data.repository.CrudRepository;
import com.proyecto.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	//Repositorio CRUD que implementa métodos automáticamente (como por ejemplo findAll y saveAll)
	
	public Usuario findByNombreDeUsuario(String nombreDeUsuario);
	
	public Usuario findByCorreo(String correo);
}
