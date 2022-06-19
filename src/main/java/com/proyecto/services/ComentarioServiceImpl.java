package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.models.Comentario;
import com.proyecto.repositories.ComentarioRepository;
import com.proyecto.services.interfaces.IComentarioService;

@Service
public class ComentarioServiceImpl implements IComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	//Obtener comentarios respecto a un cómic específico
	@Override
	@Transactional(readOnly=true)
	public List<Comentario> findByComicId(Integer idComic) {
		return (List<Comentario>) comentarioRepository.findByComicId(idComic);
	}

	//Guardar un comentario
	@Override
	@Transactional
	public Comentario save(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}
}
