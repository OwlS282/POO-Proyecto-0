/*
Instituto Tecnologico de Costa Rica

Integrantes:	Owel Jafet Gutiérrez Ortiz,		2026800869
				Miranda Jiménez Alvarado,		2026008616
				Keishiro Gabriel Cubero Abe,	2026091245
				Eduardo Alfonso Moreno Pérez,	2025080891

Profesor : Mauricio Aviles Cisneros

Semana 15 : Proyecto 0 - Sistema de Barberia

Fecha de entrega : 09 / 11 / 2026

*/

/*
Clase: Servicio
	Representa un tipo de servicio que ofrece la barbería (por ejemplo, un corte).
	Solo guarda su nombre, que lo identifica dentro del sistema y es el que se
	elige al crear o modificar una cita.
*/


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
