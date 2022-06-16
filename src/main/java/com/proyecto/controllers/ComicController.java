package com.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.Comic;
import com.proyecto.services.interfaces.IComicService;

@RestController
@RequestMapping("/comicground")
public class ComicController {

	@Autowired
	private IComicService comicService;
	
	@GetMapping("/comics")
	public ResponseEntity<?> obtenerComicsRecientes() {
		
		List<Comic> comics=null;
		try {
			comics=comicService.findLastComics();
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			//Comprobar si hay algún cómic
			comics.get(0);
		} catch(IndexOutOfBoundsException e) {
			return new ResponseEntity<String>("No se ha encontrado ningun comic", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Comic>>(comics, HttpStatus.OK);
	}

	@PostMapping("/comics")
	public ResponseEntity<?> obtenerComicsPorTitulo(@RequestBody String titulo) {
		List<Comic> comics=null;
		//Quitar las comillas
		String tituloSinComillas=titulo.replace("\"", "");
		//Si no ha escrito nada no devolver nada
		if(tituloSinComillas.equals("")) {
			return new ResponseEntity<String>("No se ha encontrado ningun comic", HttpStatus.NOT_FOUND);
		}
		
		try {
			comics=comicService.findByTitulo(tituloSinComillas);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			//Comprobar si hay algún cómic
			comics.get(0);
		} catch(IndexOutOfBoundsException e) {
			return new ResponseEntity<String>("No se ha encontrado ningun comic", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Comic>>(comics, HttpStatus.OK);
	}
	
	//	Para subir los datos de la API de ComicVine (Modificarlo para usarlo de administración)
	
//	@GetMapping("/comics/guardarNuevosComics")
//	public String saveComics() {
//		comicService.saveAll();
//		return "Se han guardado los nuevos comics";
//	}
}
