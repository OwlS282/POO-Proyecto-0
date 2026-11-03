package Logica;

public class Servicio {
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
