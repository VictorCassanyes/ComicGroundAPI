package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.models.Valoracion;
import com.proyecto.repositories.ValoracionRepository;
import com.proyecto.services.interfaces.IValoracionService;

@Service
public class ValoracionServiceImpl implements IValoracionService {

	@Autowired
	private ValoracionRepository valoracionRepository;
	
	//Obtener valoraciones respecto a un cómic específico
	@Override
	@Transactional(readOnly=true)
	public List<Valoracion> findByComicId(Integer idComic) {
		return (List<Valoracion>) valoracionRepository.findByComicId(idComic);
	}

	//Guardar una valoración si no existía ya una del mismo usuario al mismo cómic, sino solo se actualiza la puntuación
	@Override
	@Transactional
	public Valoracion save(Valoracion valoracion) {
		//Obtener valoración con mismo usuario y mismo cómic
		Valoracion valoracionBD=valoracionRepository.findByComicIdAndUsuarioId(valoracion.getComic().getId(), valoracion.getUsuario().getId());
		
		//Si no existe una, se crea nueva
		if(valoracionBD==null) {
			return valoracionRepository.save(valoracion);
		}
		
		//Si existe ya una, se actualiza la puntuación
		valoracionBD.setPuntuacion(valoracion.getPuntuacion());
		
		return valoracionRepository.save(valoracionBD);

	}
}
