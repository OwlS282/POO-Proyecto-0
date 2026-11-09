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
Clase: Horario
	Representa el horario de atención de un día de la semana: el día, la hora de
	inicio y la hora de fin. Valida que la hora de fin no sea anterior a la de
	inicio y permite consultar si una hora dada cae dentro del horario.
*/


package Logica;
//	Aportes de Keishiro, considerando que las horas de horario de
//se mantengan como localdate

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Horario implements Serializable {

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
