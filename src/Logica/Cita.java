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
Clase: Cita
	Representa un espacio reservado en la agenda. Relaciona un cliente con un
	servicio en una fecha y hora específicas, y guarda su estado
	(confirmada o no confirmada). Toda cita nueva inicia como no confirmada.
*/
package Logica;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cita implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime fechaHora;
	private Servicio servicio;
	private Cliente cliente;
	private EstadoCita estado;
	
	// Constructor. 
	public Cita(LocalDateTime fechaHora, Servicio servicio, Cliente cliente) {
		this.fechaHora = fechaHora;
		this.servicio = servicio;
		this.cliente = cliente;
		this.estado = EstadoCita.NO_CONFIRMADA; 
	}
	//getters and setters
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
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
	
	//ToString de Cita (Aporte de Keishiro)
    @Override
    public String toString(){
        return this.fechaHora + "\nCita " + this.estado + "de " + this.cliente + "\n" + this.servicio;
    }
	
	
}
