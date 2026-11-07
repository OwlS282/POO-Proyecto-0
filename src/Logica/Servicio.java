package Logica;

import java.io.Serializable;

public class Servicio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Constructor.
	private String nombre;

	public Servicio(String nombre) {
		this.nombre = nombre;
	}

	//getters and setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	//ToString de Servicio (Aporte de Keishiro)
    @Override
    public String toString(){
        return "Servicio " + this.nombre;
    }
	
	

}
