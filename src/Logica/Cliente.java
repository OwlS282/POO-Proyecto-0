package Logica;

import java.util.LinkedList;
import java.util.Map;


public class Cliente {
	private String idCliente;
	private String nombre;
	private String telefono;
	private String email;
	private LinkedList<Cita> citas;
	
	//Constructor.
	public Cliente(String idCliente, String nombre, String telefono, String email) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}
	
	//Verifican.
	public  boolean verificarEmail(String email) 
	{
		return email != null && email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
		//Habría que revisar que de verdad funcione como debería. 
	}
	public boolean verificarTelefono(String telefono)
	{
		return //Revisar
	}

	//getters and setters
	
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	// Citas, no rentaria agregarle a cada cita un entero que funcione como pos?
	
	public void agregarCita(Cliente cliente, Servicio servicio)
	{
		citas.add(null) //Y horario?
	}
	
	
	
}
