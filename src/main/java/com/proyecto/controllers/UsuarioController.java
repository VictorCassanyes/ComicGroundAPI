package com.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.Usuario;
import com.proyecto.services.interfaces.IUsuarioService;

@RestController
@RequestMapping("/ComicGround")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public List<Usuario> getUsuarios() {
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> getUsuarioById(@PathVariable Integer id) {
		Usuario usuario=null;
		try {
			usuario=usuarioService.findById(id);
		} catch(DataAccessException e) {
			//Si hay algún error en el servidor
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//Si no se encuentra al usuario
		if(usuario==null) {
			return new ResponseEntity<String>("Error, no existe ese usuario", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios/guardarUsuario")
	public ResponseEntity<String> saveUsuario(@RequestBody Usuario usuario) {
		try {
			usuarioService.save(usuario);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<String>("El usuario ha sido creado", HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/guardarUsuario/{id}")
	public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {		
		Usuario usuarioExistente=usuarioService.findById(id);
		
		if(usuarioExistente==null) {
			return new ResponseEntity<String>("Error, no existe ese usuario", HttpStatus.NOT_FOUND);
		}
		try {
			if(!usuarioService.update(usuarioExistente, usuario)) {
				return  new ResponseEntity<String>("El usuario no ha sido modificado", HttpStatus.NOT_MODIFIED);
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<String>("El usuario ha sido modificado", HttpStatus.CREATED);
	}
	
	//Si da tiempo para hacer la aplicación de escritorio añadir para borrar a un usuario
}
