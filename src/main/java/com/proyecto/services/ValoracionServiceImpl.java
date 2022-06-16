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
	
	@Override
	@Transactional(readOnly=true)
	public List<Valoracion> findByComicId(Integer idComic) {
		return (List<Valoracion>) valoracionRepository.findByComicId(idComic);
	}

	@Override
	@Transactional
	public Valoracion save(Valoracion valoracion) {
		Valoracion valoracionBD=valoracionRepository.findByComicIdAndUsuarioId(valoracion.getComic().getId(), valoracion.getUsuario().getId());
		if(valoracionBD==null) {
			return valoracionRepository.save(valoracion);
		}
		valoracionBD.setPuntuacion(valoracion.getPuntuacion());
		return valoracionRepository.save(valoracionBD);

	}
}
