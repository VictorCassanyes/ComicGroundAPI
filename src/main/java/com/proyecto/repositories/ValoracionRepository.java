package com.proyecto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.models.Valoracion;

public interface ValoracionRepository extends CrudRepository<Valoracion, Integer> {
	
	//Repositorio CRUD que implementa métodos automáticamente (como por ejemplo findAll y saveAll)
	
	public List<Valoracion> findByComicId(Integer idComic);
}
