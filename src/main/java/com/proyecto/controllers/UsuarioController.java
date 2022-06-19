package com.proyecto.controllers;

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
	
	//Para obtener un usuario según su id
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
		Usuario usuario=null;
		try {
			usuario=usuarioService.findById(id);
			//Si no existe el usuario
			if(usuario==null) {
				return new ResponseEntity<String>("No existe ningún usuario con ese id", HttpStatus.NOT_FOUND);
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return  new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	//Para iniciar sesión, devuelve el objeto usuario
	@PostMapping("/usuarios/inicioSesion")
	public ResponseEntity<?> inicioSesion(@RequestBody InicioSesion inicioSesion) {
		Usuario usuario=null;
		try {
			usuario=usuarioService.findByNombreDeUsuario(inicioSesion.getNombreDeUsuario());
			
			//Si no existe el usuario
			if(usuario==null) {
				return new ResponseEntity<String>("El usuario o la contraseña son incorrectos", HttpStatus.UNAUTHORIZED);
			}
			//Si no existe la contraseña
			if(!inicioSesion.getContrasena().equals(usuario.getContrasena())) {
				return new ResponseEntity<String>("El usuario o la contraseña son incorrectos", HttpStatus.UNAUTHORIZED);
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	//Para registrar un nuevo usuario
	@PostMapping("/usuarios/registro")
	public ResponseEntity<String> registro(@RequestHeader(required=false) String authorization, @RequestBody Usuario usuario) {
		
		try {
			//Comprobar las credenciales del cliente
			if(!usuarioService.checkAuth(authorization)) {
				return new ResponseEntity<String>("Credenciales no válidas", HttpStatus.UNAUTHORIZED);
			}
			//Comprobar si ya existe un usuario con ese nombre de usuario
			if(usuarioService.checkNombreDeUsuario(usuario)) {
				return new ResponseEntity<String>("Ya existe una cuenta con ese nombre de usuario", HttpStatus.NOT_ACCEPTABLE);
			}
			//Comprobar si ya existe un usuario con ese correo electrónico
			if(usuarioService.checkCorreo(usuario)) {
				return new ResponseEntity<String>("Ya existe una cuenta con ese correo", HttpStatus.CONFLICT);
			}
			//Guardar el usuario
			usuarioService.save(usuario);
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<String>("El usuario ha sido creado", HttpStatus.CREATED);
	}
	
	//Para actualizar un usuario existente
	@PutMapping("/usuarios/actualizar")
	public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario) {		
		Usuario usuarioExistente=usuarioService.findById(usuario.getId());
		if(usuarioExistente==null) {
			return new ResponseEntity<String>("Error, no existe ese usuario", HttpStatus.NOT_FOUND);
		}
		try {
			//Comprobar si ya existe un usuario con ese nombre de usuario
			if(usuarioService.checkNombreDeUsuario(usuario)) {
				return new ResponseEntity<String>("Ya existe una cuenta con ese nombre de usuario", HttpStatus.NOT_ACCEPTABLE);
			}
			//Actualizarlo, si no se ha modificado nada no actualizarlo
			if(!usuarioService.update(usuarioExistente, usuario)) {
				return  new ResponseEntity<String>("El usuario no ha sido modificado", HttpStatus.NOT_MODIFIED);
			} else {
				//Actualizar el usuario a devolver
				usuarioExistente=usuarioService.findById(usuario.getId());
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<Usuario>(usuarioExistente, HttpStatus.OK);
	}
}
