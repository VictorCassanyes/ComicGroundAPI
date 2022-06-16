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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.models.InicioSesion;
import com.proyecto.models.Usuario;
import com.proyecto.services.interfaces.IUsuarioService;

@RestController
@RequestMapping("/comicground")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
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
	
	
	@PostMapping("/usuarios/inicioSesion")
	public ResponseEntity<?> inicioSesion(@RequestBody InicioSesion inicioSesion) {
		Usuario usuario;
		try {
			usuario=usuarioService.findByNombreDeUsuario(inicioSesion.getNombreDeUsuario());
			
			if(!inicioSesion.getContrasena().equals(usuario.getContrasena())) {
				return new ResponseEntity<String>("El usuario o la contraseña son incorrectos", HttpStatus.UNAUTHORIZED);
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios/registro")
	public ResponseEntity<String> registro(@RequestHeader(required=false) String authorization, @RequestBody Usuario usuario) {
		
		try {
			if(!usuarioService.checkAuth(authorization)) {
				return new ResponseEntity<String>("Credenciales no válidas", HttpStatus.UNAUTHORIZED);
			}
			if(usuarioService.checkNombreDeUsuario(usuario.getNombreDeUsuario())) {
				return new ResponseEntity<String>("Ya existe una cuenta con ese nombre de usuario", HttpStatus.NOT_ACCEPTABLE);
			}
			if(usuarioService.checkCorreo(usuario.getCorreo())) {
				return new ResponseEntity<String>("Ya existe una cuenta con ese correo", HttpStatus.CONFLICT);
			}
			usuarioService.save(usuario);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<String>("El usuario ha sido creado", HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/actualizar")
	public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario) {		
		Usuario usuarioExistente=usuarioService.findById(usuario.getId());
		
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
		return  new ResponseEntity<Usuario>(usuarioExistente, HttpStatus.CREATED);
	}
	
	//Si da tiempo para hacer la aplicación de escritorio anadir para borrar a un usuario
}
