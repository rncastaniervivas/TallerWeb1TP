package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

public class Ciudad {
	private String nombre;
	
	@OneToOne (cascade = CascadeType.ALL)
	private Ubicacion ubicacionGeografica;
	
	private Pais pais;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Ubicacion getUbicacionGeografica() {
		return ubicacionGeografica;
	}
	public void setUbicacionGeografica(Ubicacion ubicacionGeografica) {
		this.ubicacionGeografica = ubicacionGeografica;
	}
	
	
}
