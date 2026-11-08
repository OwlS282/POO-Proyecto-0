package GUI;

import Control.Barberia;
import Logica.Cita;
import Logica.Cliente;
import Logica.Servicio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.*;

public class CitaDialog extends ContentDialog {
    private JComboBox<Cliente> ClienteComboBox;
    private JComboBox<Servicio> ServicioComboBox;
    private JTextField FechaField;
    private JTextField HoraField;
    private Cita cita;
    private LocalDateTime oldFechaHora;

    public CitaDialog(JFrame parent, Cita cita){
        super(parent, cita == null ? "Crear Cita" : "Modificar Cita", true);
        this.cita = cita;
        this.oldFechaHora = cita == null ? null : cita.getFechaHora();
        this.initializeDialog();
    }

    @Override
    protected void initialize(){
        this.ClienteComboBox = new JComboBox<>();
        for (Cliente c : Barberia.getInstance().getListaClientes()){
            this.ClienteComboBox.addItem(c);
        }
        this.ClienteComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus){
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof Cliente){
                    Cliente c = (Cliente) value;
                    this.setText(c.getIdCliente() + " - " + c.getNombre());
                }
                return this;
            }
        });
        this.ServicioComboBox = new JComboBox<>();
        for (Servicio s : Barberia.getInstance().getListaServicio()){
            this.ServicioComboBox.addItem(s);
        }
        this.FechaField = new JTextField(LocalDate.now().toString(), 20);
        this.HoraField = new JTextField("10:00", 20);
        if (this.cita != null){
            this.ClienteComboBox.setSelectedItem(this.cita.getCliente());
            this.ClienteComboBox.setEnabled(false);
            this.ServicioComboBox.setSelectedItem(this.cita.getServicio());
            this.FechaField.setText(this.cita.getFechaHora().toLocalDate().toString());
            this.HoraField.setText(this.cita.getFechaHora().toLocalTime().toString());
        }
        this.Contents.add(new JLabel("Cliente:"));
        this.Contents.add(this.ClienteComboBox);
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Servicio:"));
        this.Contents.add(this.ServicioComboBox);
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Fecha (aaaa-MM-dd):"));
        this.Contents.add(this.FechaField);
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Hora (HH:mm):"));
        this.Contents.add(this.HoraField);
        this.Contents.add(Box.createVerticalStrut(10));
        if (this.ClienteComboBox.getItemCount() == 0 || this.ServicioComboBox.getItemCount() == 0){
            this.Contents.add(new JLabel("Primero registre clientes y servicios."));
            this.ConfirmarButton.setEnabled(false);
        }
        this.Contents.add(new JLabel("El dia debe tener un horario de atencion configurado en Admin."));
    }

    @Override
    protected void onConfirm(){
        try{
            Cliente cliente = (Cliente) this.ClienteComboBox.getSelectedItem();
            Servicio servicio = (Servicio) this.ServicioComboBox.getSelectedItem();
            if (cliente == null || servicio == null){
                throw new Exception("Seleccione un cliente y un servicio.");
            }
            LocalDate fecha = LocalDate.parse(this.FechaField.getText().trim());
            LocalTime hora = LocalTime.parse(this.HoraField.getText().trim());
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
            if (this.cita == null){
                Barberia.getInstance().crearCita(cliente.getIdCliente(), servicio, fechaHora);
            }
            else {
                Barberia.getInstance().modificarCita(this.oldFechaHora, fechaHora, servicio);
            }
            this.dispose();
        }
        catch (DateTimeParseException exc){
            this.showErrorMessage("Fecha u hora invalida", new Exception("Ingrese una fecha aaaa-MM-dd y una hora HH:mm validas."));
        }
        catch (Exception exc){ this.showErrorMessage("Error al guardar cita", exc); }
    }
}
