package Logica;
	
import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	
	
public class Cliente implements Serializable {
		/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	
	private String idCliente;
	private String nombre;
	private String telefono;
	private String email;
		
	//Constructor.
	public Cliente(String idCliente, String nombre, String telefono, String email) throws Exception {
		if (!validarEmail(email)){
		     throw new Exception("Email invalido.");
		 }
		if (!validarTelefono(telefono)){
		     throw new Exception("Telefono invalido.");
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
	
	 @Override
	 public String toString(){
	     return "Cliente " + this.idCliente + ": " + this.nombre + " | Email: " + this.email + " | Tel: " + this.telefono;
	 }
}
