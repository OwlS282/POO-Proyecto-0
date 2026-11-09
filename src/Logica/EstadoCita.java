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
Clase: EstadoCita
	Enumeración con los dos estados posibles de una cita: NO_CONFIRMADA y
	CONFIRMADA. Su texto en pantalla se define en toString().
*/


package Logica;

public enum EstadoCita {
	NO_CONFIRMADA, 
	CONFIRMADA;
	
	//ToString de EstadoCita (Aporte de Keishiro)
	 @Override    
	 public String toString(){
	     switch (this){
	         case CONFIRMADA:
	             return "Confirmada";
	         case NO_CONFIRMADA:
	             return "No confirmada";
	         default:
	             return "???";
	     }	
	 }

}
