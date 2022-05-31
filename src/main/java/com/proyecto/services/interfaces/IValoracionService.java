package com.proyecto.services.interfaces;

import java.util.List;

import com.proyecto.models.Valoracion;

public interface IValoracionService {

	public List<Valoracion> findByComicId(Integer idComic);
	
	public Valoracion save(Valoracion valoracion);
}
