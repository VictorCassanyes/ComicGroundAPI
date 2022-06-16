package com.proyecto.services.interfaces;

import java.util.List;

import com.proyecto.models.Comic;

public interface IComicService {
	
	public List<Comic> findLastComics();
	
	public void saveAll();
	
	public List<Comic> findByTitulo(String titulo);
}
