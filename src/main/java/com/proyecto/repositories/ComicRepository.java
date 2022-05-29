package com.proyecto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.models.Comic;

public interface ComicRepository extends CrudRepository<Comic, Integer> {
	
	//Repositorio CRUD que implementa métodos automáticamente (como por ejemplo findAll y saveAll)
	
	public List<Comic> findByTituloContaining(String titulo);
}
