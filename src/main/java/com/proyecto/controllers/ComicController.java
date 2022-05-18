package com.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.entity.Comic;
import com.proyecto.models.entity.IComicService;

@CrossOrigin(origins= {"http://localhost:8080"})
@RestController
@RequestMapping("/ComicGround")
public class ComicController {

	@Autowired
	private IComicService comicService;
	
	@GetMapping("/comics")
	public List<Comic> index() {
		return comicService.findAll();
	}
}
