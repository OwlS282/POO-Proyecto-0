package Logica;
//	Aportes de Keishiro, considerando que las horas de horario de
//se mantengan como localdate

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Horario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DayOfWeek dia;
	private LocalTime horaInicio;
	private LocalTime horaFin;

	public Horario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin) throws Exception {
	    if (horaFin.isBefore(horaInicio)){
	        throw new Exception("Horario invalido. La hora de fin termina antes que la hora de inicio.");
	    }

	    this.dia = dia;
	    this.horaInicio = horaInicio;
	    this.horaFin = horaFin;
	}

	public DayOfWeek getDia() {
	    return dia;
	}

	public void setDia(DayOfWeek dia) {
	    this.dia = dia;
	}

	public LocalTime getHoraInicio() {
	    return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
	    this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
	    return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
	    this.horaFin = horaFin;
	}
	public boolean enRango(LocalTime hora){
	    return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
	}

	@Override
	public String toString(){
	    return "Horario de " + this.dia + ". Inicio: " + this.horaInicio + ". Fin: " + this.horaFin;
	}

}
