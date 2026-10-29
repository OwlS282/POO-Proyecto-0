package Logica;

public class Cita 
{
	private Integer fechaHora; //Suponiendo que lo usemos como dijo el prfe
	private Servicio servicio;
	private Cliente cliente;
	private EstadoCita estado;
	
	// Constructor. 
	public Cita(Integer fechaHora, Servicio servicio, Cliente cliente) {
		super();
		this.fechaHora = fechaHora;
		this.servicio = servicio;
		this.cliente = cliente;
		// El estado debería iniciar como no confirmado?  
	}
	//getters and setters
	public Integer getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Integer fechaHora) {
		this.fechaHora = fechaHora;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public EstadoCita getEstado() {
		return estado;
	}
	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}
	//To String. 
	@Override
	public String toString() { //No estoy segura si tiene que ser de una forma especifica.
	}
	
	
}
