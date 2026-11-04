package Control;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import Logica.Cita;
import Logica.Cliente;
import Logica.Horario;
import Logica.Servicio;

public class Barberia {
	private static Barberia instancia;
	private int contadorCitas;
	
	private Map<String, Cliente> clientes;
	private Map<String, Servicio> servicios;
	private Map<LocalDateTime, Cita> citas;
	private Queue<Cliente> listaEspera;
	private Map<DayOfWeek, Horario> horarios;
	
	private Barberia() {
		contadorCitas = 0;
		clientes = new HashMap<>();
		servicios = new HashMap<>();
		citas = new TreeMap<>();
		listaEspera  = new LinkedList<>();
		horarios = new HashMap<>();
	}
	
	public static Barberia getInstance() {
		if (instancia == null) {
			instancia = new Barberia();
		}
		return instancia;
	}
	
	public void cargarDatosDisco() {}

	public void guardarDatosDisco() {}
	
	public Cliente crearCliente(String id, String nombre, String telefono, String email) {}
	
	public Cliente consultarCliente(String id) {}
	
	public Boolean modificarCliente(String id, String nombre, String telefono, String email) {}
	
	public Boolean borrarCliente(String id) {}
	
	public List<Cliente> getListaClientes() {}
	
	public Servicio crearServicio(String id, String nombre) {}
	
	public Servicio consultarServicio(String id) {}
	
	public Boolean modificarServicio(String id, String nombre) {}
	
	public Boolean borrarServicio(String id) {}
	
	public List<Servicio> getListaServicios() {}
	
	public Cita crearCita(String idCliente, Servicio servicio, LocalDateTime fechaHora) {}
	
	public Cita consultarCita(LocalDateTime fechaHora) {}
	
	public Boolean modificarCita(LocalDateTime fechaHora, Servicio servicio) {}
	
	public Boolean borrarCita(LocalDateTime fechaHora) {}
	
	public Boolean confirmarCita(LocalDateTime fechaHora) {}
	
	public Boolean enviarNotificacion(LocalDateTime fechaHora) {}
	
	public Boolean enviarNotificacionesConfirmacion() {}
	
	public List<Cita> getCalendarioCitas(LocalDate desde, LocalDate hasta) {}
	
	public Boolean agregarClienteListaEspera(String idCliente) {}
	
	public Boolean borrarClienteListaEspera(String idCliente) {}
	
	public Queue<Cliente> getListaEspera() {}
	
	public void establecerHorario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin) {}
	
	public Boolean clienteEnSistema(Cliente cliente) {}
	
	public Boolean enHorarioDeAtencion(LocalDateTime fechaHora) {}
	
	public Boolean servicioEnUso(Servicio servicio) {}

}