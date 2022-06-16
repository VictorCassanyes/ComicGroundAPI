package com.proyecto.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="valorar")
@NamedQueries({	
	@NamedQuery(
			name="ValoracionRepository.findByComicId",
			query="select v from Valoracion v where comic.id like ?1"
		),
	@NamedQuery(
			name="ValoracionRepository.findValoracion",
			query="select v from Valoracion v where comic.id like ?1 and usuario.id like ?2"
		)
})
public class Valoracion implements Serializable {

	private static final long serialVersionUID = 1700429166736660996L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="id_comic")
	private Comic comic;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	private float puntuacion;

	public Valoracion() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
