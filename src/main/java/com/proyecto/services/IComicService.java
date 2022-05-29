package com.proyecto.services;

import java.util.List;

import com.proyecto.models.Comic;


public interface IComicService {
	
	public List<Comic> findAll();
	
	public void saveAll();
	
	public List<Comic> findByTitulo(String titulo);
}
