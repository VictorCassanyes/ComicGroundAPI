package com.proyecto.services.interfaces;

import java.util.List;

import com.proyecto.models.Comentario;

public interface IComentarioService {

	public List<Comentario> findByComicId(Integer idComic);
	
	public Comentario save(Comentario comentario);
}
