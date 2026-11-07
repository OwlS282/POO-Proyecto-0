package Control;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

import Logica.Cita;
import Logica.Cliente;
import Logica.EstadoCita;
import Logica.Horario;
import Logica.Servicio;

public class Barberia implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String dataFileName = "DataBarberia.dat";

    private static Barberia instance;
    private Map<DayOfWeek, Horario> horarios;
    private Map<String, Servicio> servicios;
    private Map<LocalDateTime, Cita> citas;
    private Map<String, Cliente> clientes;
    private Queue<Cliente> espera;

    private final static String CORREO_LOCAL = "barberia@gmail.com";
    private final static String CLAVE_CORREO = "la clave de 16 letras sin espacios";
    
    private Barberia(){
        instance = null;

        this.horarios = new HashMap<>();
        this.servicios = new HashMap<>();
        this.citas = new HashMap<>();
        this.clientes = new HashMap<>();
        this.espera = new ArrayDeque<>();
    }

    public static Barberia getInstance(){
        if (instance == null){
            instance = new Barberia();
        }
        return instance;
    }

    public void cargarDatosDisco() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(dataFileName);
        ObjectInputStream stream = new ObjectInputStream(file);
        instance = (Barberia)stream.readObject();
        stream.close();
        file.close();
    }
    public void guardarDatosDisco() throws IOException {
        FileOutputStream file = new FileOutputStream(dataFileName);
        ObjectOutputStream stream = new ObjectOutputStream(file);
        stream.writeObject(instance);
        stream.close();
        file.close();
    }

    public Cliente crearCliente(String nombre, String tel, String email) throws Exception {
        String id = Integer.toString(this.clientes.size() + 1);
        if (this.clientes.containsKey(id)){
            throw new Exception("ID de cliente ya existente.");
        }
        Cliente c = new Cliente(id, nombre, email, tel);
        this.clientes.put(id, c);
        return c;
    }
    public Cliente consultarCliente(String id) throws Exception {
        if (!this.clientes.containsKey(id)){
            throw new Exception("ID de cliente no existente.");
        }
        return this.clientes.get(id);
    }
    public void modificarCliente(String id, String nombre, String telefono, String email) throws Exception {
        Cliente c = this.consultarCliente(id);
        c.setNombre(nombre);
        c.setEmail(email);
        c.setTelefono(telefono);
    }
    public boolean borrarCliente(String id) throws Exception {
        if (!this.clientes.containsKey(id)){
            throw new Exception("ID de cliente no existente.");  // return false?
        }
        this.clientes.remove(id);
        return true;
    }
    public ArrayList<Cliente> getListaClientes(){
        return (ArrayList<Cliente>)this.clientes.values();
    }

    public Servicio crearServicio(String nombre) throws Exception {
        // CAMBIADO: Parametro String id no existe.
        if (this.servicios.containsKey(nombre)){
            throw new Exception("Servicio ya existente.");
        }
        Servicio s = new Servicio(nombre);
        this.servicios.put(nombre, s);
        return s;
    }
    public Servicio consultarServicio(String nombre) throws Exception {
        // CAMBIADO: Parametro String id no existe.
        if (!this.servicios.containsKey(nombre)){
            throw new Exception("Servicio inexistente.");
        }
        return this.servicios.get(nombre);
    }
    public boolean modificarServicio(String oldNombre, String nombre) throws Exception {
        // CAMBIADO: De Parametro inexistente String id -> String oldNombre
        if (this.servicios.containsKey(nombre)){
            throw new Exception("Servicio ya existente.");
        }
        Servicio s = this.consultarServicio(oldNombre);
        this.servicios.remove(oldNombre);

        s.setNombre(nombre);
        this.servicios.put(nombre, s);
        return true;
    }
    public boolean borrarServicio(String nombre) throws Exception {
        if (!this.servicios.containsKey(nombre)){
            throw new Exception("Servicio inexistente.");  // return false?
        }
        this.servicios.remove(nombre);
        return true;
    }
    public ArrayList<Servicio> getListaServicio() {
        return (ArrayList<Servicio>)this.servicios.values();
    }

    public Cita crearCita(String idCliente, Servicio servicio, LocalDateTime fechaHora) throws Exception {
        if (!this.enHorarioDeAtencion(fechaHora)){
            throw new Exception("Hora o fecha no disponible.");
        }
        if (this.citas.containsKey(fechaHora)){
            throw new Exception("Cita en hora ya existente.");
        }
        Cliente c = this.consultarCliente(idCliente);
        Cita cita = new Cita(fechaHora, servicio, c );
        this.citas.put(fechaHora, cita);
        return cita;
    }
    public Cita consultarCita(LocalDateTime fechaHora) throws Exception {
        if (!this.citas.containsKey(fechaHora)){
            throw new Exception("Cita en hora inexistente.");
        }
        return this.citas.get(fechaHora);
    }
    public boolean modificarCita(LocalDateTime oldFechaHora, LocalDateTime fechaHora, Servicio servicio) throws Exception {
        // CAMBIADO: Añadido parametro LocalDateTime oldFechaHora.
        if (!this.enHorarioDeAtencion(fechaHora)) {
            throw new Exception("Hora o fecha no disponible.");
        }
        Cita cita = this.consultarCita(fechaHora);
        this.citas.remove(fechaHora);

        cita.setServicio(servicio);
        cita.setFechaHora(fechaHora);
        this.citas.put(fechaHora, cita);
        return true;
    }
    public boolean borrarCita(LocalDateTime fechaHora) throws Exception {
        if (!this.citas.containsKey(fechaHora)){
            throw new Exception("Cita en hora inexistente.");
        }
        this.citas.remove(fechaHora);
        return true;
    }
    public boolean confirmarCita(LocalDateTime fechaHora) throws Exception {
        Cita cita = this.consultarCita(fechaHora);
        cita.setEstado(EstadoCita.CONFIRMADA);
        return true;
    }
    
    public boolean enviarNotificacion(LocalDateTime fechaHora) throws Exception {
    	// Busca la cita en el mapa, si no existe, consultarCita lanza una excepción.
        Cita cita = this.consultarCita(fechaHora);
        
        /*
        // me parece innecesario 
        if (cita.getEstado() == EstadoCita.CONFIRMADA) {
            throw new Exception("La cita ya está confirmada.");
        }
        */
        
        // Properties es un diccionario de configuración (clave -> valor) para la conexión.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");              // el servidor exige usuario y clave
        props.put("mail.smtp.starttls.enable", "true");   // cifra la conexión (STARTTLS)
        props.put("mail.smtp.host", "smtp.gmail.com");    // servidor de correo saliente de Gmail
        props.put("mail.smtp.port", "587");               // puerto de Gmail para STARTTLS
        
        

        // Session representa la "sesión de correo": junta la configuración y las credenciales.
        // El Authenticator es quien entrega el usuario y la clave cuando el servidor las pide.
        Session sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Usuario (correo de la barbería) y la contraseña de aplicación de Gmail.
                return new PasswordAuthentication(CORREO_LOCAL, CLAVE_CORREO);
            }
        });

        // Convierte la fecha de la cita en texto legible, por ejemplo "07/11/2026 a las 10:00".
        // Las comillas simples en 'a las' hacen que ese texto se escriba tal cual.
        String fecha = cita.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"));        
        
        // Crea un mensaje vacío asociado a la sesión.
        Message mensaje = new MimeMessage(sesion);
        
        // Remitente: la cuenta de la barbería.
        mensaje.setFrom(new InternetAddress(CORREO_LOCAL));
        
        // Destinatario: el email del cliente de la cita.
        // parse() convierte el texto en un arreglo de direcciones válidas.
        mensaje.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(cita.getCliente().getEmail()));
        
        // Asunto del correo.
        mensaje.setSubject("Confirmación de cita");
        
        // Cuerpo del correo: saludo con el nombre, la fecha y la instrucción de confirmar.
        // \n\n son dos saltos de línea.
        mensaje.setText("Hola " + cita.getCliente().getNombre() + ",\n\n"
                + "Tiene una cita programada para el " + fecha + ".\n"
                + "Por favor comuníquese con el local para confirmarla.");

        // Aquí se hace el envío real: se conecta a Gmail, se autentica y manda el mensaje.
        // Si falla (sin internet, clave incorrecta, etc.), lanza una excepción.
        Transport.send(mensaje);
        // Si llegó hasta aquí sin excepción, el envío fue exitoso.
        return true;
    }
    
    public ArrayList<Cita> enviarNotificacionesConfirmacion() throws Exception {
        // Fecha de mañana: hoy más un día.
        LocalDate manana = LocalDate.now().plusDays(1);
        // Lista donde se irán guardando las citas sin confirmar.
        ArrayList<Cita> pendientes = new ArrayList<>();
        // Recorre las citas de mañana (desde mañana hasta mañana = solo ese día).
        for (Cita c : this.getCalendarioCitas(manana, manana)) {
        	// Se queda solo con las que todavía no están confirmadas.
            if (c.getEstado() == EstadoCita.NO_CONFIRMADA) {
                pendientes.add(c);
            }
        }
        // Si no hay ninguna, el caso de uso pide mostrar un error.
        if (pendientes.isEmpty()) {
            throw new Exception("No hay citas programadas para el día siguiente.");
        }
        // Devuelve la lista para que la interfaz recorra cada cita y llame a enviarNotificacion.
        return pendientes;
    }
    
    public ArrayList<Cita> getCalendarioCitas(LocalDate desde, LocalDate hasta) {
        ArrayList<Cita> res = new ArrayList<>();
        if (desde.isAfter(hasta)){
            return res;
        }

        for (Cita c : this.citas.values()){
            LocalDate fechaCita = c.getFechaHora().toLocalDate();
            if (!fechaCita.isBefore(desde) && !fechaCita.isAfter(hasta)){
                res.add(c);
            }
        }
        return res;
    }
    
    public boolean agregarClienteListaEspera(String idCliente) throws Exception {
        Cliente c = this.consultarCliente(idCliente);
        if (!this.espera.contains(c)){
            this.espera.add(c);
            return true;
        }
        return false;
    }
    
    public boolean borrarClienteListaEspera() throws Exception {
        if (this.espera.isEmpty()) {
            return false;
        }
        this.espera.remove();
        return true;
    }
    
    public Queue<Cliente> getListaEspera(){
        return this.espera;
    }
    
    public void establecerHorario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFinal) throws Exception {
        Horario h = new Horario(dia, horaInicio, horaFinal);
        if (this.horarios.containsKey(dia)){
            this.horarios.remove(dia);
        }
        this.horarios.put(dia, h);
    }
    
    public boolean clienteEnSistema(Cliente cliente){
        return this.clientes.containsValue(cliente);
    }
    
    public boolean enHorarioDeAtencion(LocalDateTime fechaHora){
        DayOfWeek day = fechaHora.getDayOfWeek();
        if (!this.horarios.containsKey(day)){
            return false;
        };
        Horario h = this.horarios.get(fechaHora.getDayOfWeek());
        return h.enRango(fechaHora.toLocalTime());
    }
    
    public boolean servicioEnUso(Servicio servicio){
        for (Cita c : this.citas.values()){
            if (c.getServicio() == servicio){
                return true;
            }
        }
        return false;
    }
}