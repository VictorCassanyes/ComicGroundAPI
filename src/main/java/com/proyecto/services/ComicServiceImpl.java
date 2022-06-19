package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.models.Comic;
import com.proyecto.repositories.ComicRepository;
import com.proyecto.services.interfaces.IComicService;

@Service
public class ComicServiceImpl implements IComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	//Obtener los últimos 100 cómics de la base de datos
	@Override
	@Transactional(readOnly=true)
	public List<Comic> findLastComics() {
		return (List<Comic>) comicRepository.findTop100ByOrderByIdDesc();
	}

	//Obtener hasta 100 cómics encontrados que contengan en su título el String
	@Override
	@Transactional(readOnly=true)
	public List<Comic> findByTitulo(String titulo) {
		return comicRepository.findTop100ByTituloContaining(titulo);
	}
	
	//	Para guardar nuevos cómics obtenidos de ComicVine (Pensado para ser usado por los administradores)
	//	@Override
	//	@Transactional
	//	public void saveAll() {
	//		try {
	//			//Leer el archivo JSON el cual contiene un array de todos los comics a guardar, y meterlo en un String
	//			FileReader fr=new FileReader("C:/Users/victo/Desktop/datosFinales.json");
	//			int valor;
	//	        String comicsArray="";
	//	        while((valor=fr.read())!=-1){
	//	            comicsArray+=(char)valor;
	//	        }
	//	        fr.close();
	//	        
	//	        //Pasar datos del String en formato JSON a un ArrayList de comics
	//	        Gson gson=new Gson(); 
	//	        Type comicListType=new TypeToken<ArrayList<Comic>>(){}.getType();
	//	        ArrayList<Comic> comics=gson.fromJson(comicsArray, comicListType); 
	//	        
	//	        //Guardar los comics en la base de datos
	//	        comicRepository.saveAll(comics);
	//	        
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}
}
