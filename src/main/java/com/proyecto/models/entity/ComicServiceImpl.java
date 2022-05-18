package com.proyecto.models.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComicServiceImpl implements IComicService {

	
	@Autowired
	private ComicRepository comicRepository;
	
	@Override
	public List<Comic> findAll() {
		return (List<Comic>) comicRepository.findAll();
	}
}
