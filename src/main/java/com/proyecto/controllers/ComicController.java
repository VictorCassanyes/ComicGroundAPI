package com.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.Comic;
import com.proyecto.services.IComicService;

@CrossOrigin(origins= {"http://192.168.0.11:8080"})
@RestController
@RequestMapping("/ComicGround")
public class ComicController {

	@Autowired
	private IComicService comicService;
	
	@GetMapping("/comics")
	public List<Comic> getComics() {
		return comicService.findAll();
	}
	
	@GetMapping("/comics/guardarNuevosDatos")
	public String saveComics() {
		comicService.saveAll();
		return "Se han guardado los nuevos comics";
	}
	
	@GetMapping("/comics/{titulo}")
	public List<Comic> getComicsByTitulo(@PathVariable String titulo) {
		return comicService.findByTitulo(titulo);
	}
	
	@GetMapping("/error")
	public String showError() {
		return "Ha ocurrido un error";
	}
}
