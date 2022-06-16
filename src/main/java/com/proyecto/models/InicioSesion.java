package com.proyecto.models;

public class InicioSesion {

	private String nombreDeUsuario;
	
	private String contrasena;
	
	public InicioSesion(String nombreDeUsuario, String contrasena) {
		super();
		this.nombreDeUsuario = nombreDeUsuario;
		this.contrasena = contrasena;
	}

	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}

	public void setNombreDeUsuario(String nombreDeUsuario) {
		this.nombreDeUsuario = nombreDeUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
}
