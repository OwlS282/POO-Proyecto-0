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
Clase: Cliente
	Representa a una persona registrada en la barbería. Guarda su identificador,
	nombre, teléfono y correo electrónico. Valida que el correo y el teléfono
	tengan un formato válido antes de aceptar los datos. Es la clase a la que
	apuntan las citas y la lista de espera.
*/

package Logica;
	
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	
	
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String idCliente;
	private String nombre;
	private String telefono;
	private String email;
		
	//Constructor.
	public Cliente(String idCliente, String nombre, String telefono, String email) throws Exception {
		if (nombre == null || nombre.trim().isEmpty()){
		    throw new Exception("Error, El nombre no puede estar vacio.");
		}
		if (!validarEmail(email)){
		     throw new Exception("Error, Email invalido.");
		 }
		if (!validarTelefono(telefono)){
		     throw new Exception("Error, Telefono invalido.");
		 }
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}
		
	//Verificación (Aportes de Keishiro).
	public boolean validarEmail(String email){
		if (email == null) { return false; }
	     Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
	     Matcher m = p.matcher(email);
	     return m.matches();
	}
		 
	public boolean validarTelefono(String telefono){
		if (telefono == null) { return false; }
	     Pattern p = Pattern.compile("^[0-9]{4}[-\\s]?[0-9]{4}$");
	     Matcher m = p.matcher(telefono);
	     return m.matches();
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
	
	public void setNombre(String nombre) throws Exception {
	    if (nombre == null || nombre.trim().isEmpty()){
	        throw new Exception("El nombre no puede estar vacio.");
	    }
	    this.nombre = nombre;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) throws Exception {
	    if (telefono == null || telefono.trim().isEmpty()){
	        throw new Exception("El telefono no puede estar vacio.");
	    }
	    if (!validarTelefono(telefono)){
	        throw new Exception("Telefono invalido. Use el formato 8888-8888.");
	    }
	    this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) throws Exception {
	    if (email == null || email.trim().isEmpty()){
	        throw new Exception("El email no puede estar vacio.");
	    }
	    if (!validarEmail(email)){
	        throw new Exception("Email invalido. Debe ser como nombre@dominio.com.");
	    }
	    this.email = email;
	}
	
	 @Override
	 public String toString(){
	     return "Cliente " + this.idCliente + ": " + this.nombre + " | Email: " + this.email + " | Tel: " + this.telefono;
	 }
}
