package com.proyecto.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
@Entity
@Table(name="comics")
	//Query propia. Sirve para encontrar comics que contengan en su título el parámetro dado
@NamedNativeQuery(
			name="ComicRepository.findTop100ByTituloContaining",
			query="select c from Comic c where titulo like '%?%'"
		)
public class Comic implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@SerializedName("name")
	private String titulo;
	
	@SerializedName("image")
	private String portada;
	
	public Comic() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}
	
}