package com.proyecto.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -57097122962998182L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String correo;
	
	@Column(name="nombre_usuario")
	private String nombreDeUsuario;
	
	private String nombre;
	
	private String apellidos;
	
	private String contraseña;
	
	private boolean habilitado;
	
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="usuarios_roles", joinColumns=@JoinColumn(name="id_usuario"), inverseJoinColumns=@JoinColumn(name="id_rol"), 
	uniqueConstraints= {@UniqueConstraint(columnNames={"id_usuario", "id_rol"})})
	private List<Rol> roles;
	
	public Usuario() {}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}

	public void setNombreDeUsuario(String nombreDeUsuario) {
		this.nombreDeUsuario = nombreDeUsuario;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isHabilitado() {
		return habilitado;
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}
