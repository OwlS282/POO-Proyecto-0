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
	private List<Cita> citas;
		
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
		this.citas = new ArrayList<Cita>();
	}
		
	//Verificación (Aportes de Keishiro).
	public boolean validarEmail(String email){
	     Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	     Matcher m = p.matcher(email);
	     return m.matches();
	}
		 
	public boolean validarTelefono(String telefono){
	     Pattern p = Pattern.compile("^[0-9]{4}[-|\\s]?[0-9]{4}$", Pattern.CASE_INSENSITIVE);
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
		
	public void agregarCita(Cita cita){
		 citas.add(cita);
	 }
		
		//ToString y borrar cita son aportes de Keishiro.
	public void borrarCita(LocalDateTime fechaHora) throws Exception{
		 for (Cita c : this.citas) {
			 if (c.getFechaHora().equals(fechaHora)) {
				 this.citas.remove(c);
				 return;
			 }
		}
		throw new Exception("No se enontró la cita");
	 }
	
	 @Override
	 public String toString(){
	     return "Cliente " + this.idCliente + ": " + this.nombre + " | Email: " + this.email + " | Tel: " + this.telefono;
	 }
}
