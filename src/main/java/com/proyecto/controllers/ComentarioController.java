package com.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.Comentario;
import com.proyecto.services.interfaces.IComentarioService;

@RestController
@RequestMapping("/comicground")
public class ComentarioController {

	
	@Autowired
	private IComentarioService comentarioService;
	
	//Para encontrar todos los comentarios hechos sobre un cómic
	@GetMapping("/comentarios/{idComic}")
	public ResponseEntity<?> obtenerComentariosPorIdDeComic(@PathVariable Integer idComic) {	
		List<Comentario> comentarios=null;
		
		try {
			comentarios=comentarioService.findByComicId(idComic);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			comentarios.get(0);
		} catch(IndexOutOfBoundsException e) {
			return new ResponseEntity<String>("No hay ningun comentario respecto a este comic", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
	}
	
	//Para guardar un nuevo comentario
	@PostMapping("/comentarios/comentar")
	public ResponseEntity<String> comentar(@RequestBody Comentario comentario) {
		try {
			comentarioService.save(comentario);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("El comentario ha sido agregado", HttpStatus.CREATED);
	}
}
