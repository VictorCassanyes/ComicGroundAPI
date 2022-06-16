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

import com.proyecto.models.Valoracion;
import com.proyecto.services.interfaces.IValoracionService;

@RestController
@RequestMapping("/comicground")
public class ValoracionController {
	
	@Autowired
	private IValoracionService valoracionService;
	
	@GetMapping("/valoraciones/{idComic}")
	public ResponseEntity<?> obtenerValoracionesPorIdDeComic(@PathVariable Integer idComic) {
		List<Valoracion> valoraciones=null;
		try {
			valoraciones=valoracionService.findByComicId(idComic);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			valoraciones.get(0);
		} catch(IndexOutOfBoundsException e) {
			return new ResponseEntity<String>("Nadie ha valorado este comic", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Valoracion>>(valoraciones, HttpStatus.OK);
	}
	
	@PostMapping("/valoraciones/valorar")
	public ResponseEntity<String> valorar(@RequestBody Valoracion valoracion) {
		try {
			valoracionService.save(valoracion);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("La valoración ha sido agregada", HttpStatus.CREATED);
	}
}
