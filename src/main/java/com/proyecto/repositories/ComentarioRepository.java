package com.proyecto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.models.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {
	
	//Repositorio CRUD que implementa métodos automáticamente (como por ejemplo findAll y saveAll)
	
	public List<Comentario> findByComicId(Integer idComic);
}
