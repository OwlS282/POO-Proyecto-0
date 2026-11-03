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
