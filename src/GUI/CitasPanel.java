package GUI;

import Control.Barberia;
import Logica.Cita;
import Logica.EstadoCita;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class CitasPanel extends ContentPanel {
    private JTable CitasTable;
    private DefaultTableModel CitasTableModel;
    private JTextField DesdeField;
    private JTextField HastaField;
    private JLabel VistaLabel;
    private JButton ConsultarCalendarioButton;
    private JButton PendientesButton;
    private JButton CrearCitaButton;
    private JButton ConsultarCitaButton;
    private JButton ModificarCitaButton;
    private JButton BorrarCitaButton;
    private JButton ConfirmarCitaButton;
    private ArrayList<Cita> citasMostradas;
    private boolean mostrandoPendientes;

    public CitasPanel(){
        this.setLayout(new BorderLayout(0, 0));
        this.citasMostradas = new ArrayList<>();
        JPanel TopPanel = new JPanel();
        TopPanel.setLayout(new BoxLayout(TopPanel, BoxLayout.Y_AXIS));
        JPanel FiltrosPanel = new JPanel();
        FiltrosPanel.setForeground(new Color(255, 255, 255));
        FiltrosPanel.setBackground(new Color(0, 0, 64));
        this.DesdeField = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 10);
        this.HastaField = new JTextField(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).toString(), 10);
        this.ConsultarCalendarioButton = new JButton("Consultar Calendario");
        this.PendientesButton = new JButton("Pendientes de Mañana");
        JLabel label = new JLabel("Desde (aaaa-MM-dd):");
        label.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 14));
        label.setForeground(new Color(255, 255, 255));
        FiltrosPanel.add(label);
        FiltrosPanel.add(this.DesdeField);
        JLabel label_1 = new JLabel("Hasta:");
        label_1.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 14));
        label_1.setForeground(new Color(255, 255, 255));
        FiltrosPanel.add(label_1);
        FiltrosPanel.add(this.HastaField);
        FiltrosPanel.add(this.ConsultarCalendarioButton);
        FiltrosPanel.add(this.PendientesButton);
        TopPanel.add(FiltrosPanel);
        JPanel TopButtonsPanel = new JPanel();
        this.CrearCitaButton = new JButton("Crear Cita");
        CrearCitaButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        CrearCitaButton.setForeground(new Color(255, 255, 255));
        CrearCitaButton.setBackground(new Color(159, 32, 32));
        this.ConsultarCitaButton = new JButton("Consultar Cita");
        ConsultarCitaButton.setForeground(new Color(255, 255, 255));
        ConsultarCitaButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        ConsultarCitaButton.setBackground(new Color(159, 32, 32));
        this.ModificarCitaButton = new JButton("Modificar Cita");
        ModificarCitaButton.setBackground(new Color(159, 32, 32));
        ModificarCitaButton.setForeground(new Color(255, 255, 255));
        ModificarCitaButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        this.BorrarCitaButton = new JButton("Cancelar Cita");
        BorrarCitaButton.setForeground(new Color(255, 255, 255));
        BorrarCitaButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        BorrarCitaButton.setBackground(new Color(159, 32, 32));
        this.ConfirmarCitaButton = new JButton("Confirmar Cita");
        ConfirmarCitaButton.setForeground(new Color(255, 255, 255));
        ConfirmarCitaButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        ConfirmarCitaButton.setBackground(new Color(159, 32, 32));
        TopButtonsPanel.add(this.CrearCitaButton);
        TopButtonsPanel.add(this.ConsultarCitaButton);
        TopButtonsPanel.add(this.ModificarCitaButton);
        TopButtonsPanel.add(this.BorrarCitaButton);
        TopButtonsPanel.add(this.ConfirmarCitaButton);
        TopPanel.add(TopButtonsPanel);
        this.VistaLabel = new JLabel("Calendario de citas");
        VistaLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        VistaLabel.setForeground(new Color(0, 0, 64));
        TopPanel.add(this.VistaLabel);
        this.add(TopPanel, BorderLayout.NORTH);
        this.CitasTableModel = new DefaultTableModel(new Object[]{"Fecha", "Hora", "ID Cliente", "Cliente", "Servicio", "Estado"}, 0);
        this.CitasTable = new JTable(this.CitasTableModel);
        this.CitasTable.setDefaultEditor(Object.class, null);
        this.CitasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.CitasTable.getSelectionModel().addListSelectionListener(e -> {
            this.updateSelectionButtons();
        });
        this.add(new JScrollPane(this.CitasTable), BorderLayout.CENTER);
        this.updateSelectionButtons();
        this.connectButtonsActionEvents();
    }

    private Cita getSelectedCita() throws Exception {
        int row = this.CitasTable.getSelectedRow();
        if (row == -1){ throw new Exception("Seleccione una cita."); }
        Cita c = this.citasMostradas.get(this.CitasTable.convertRowIndexToModel(row));
        return Barberia.getInstance().consultarCita(c.getFechaHora());
    }

    private void updateSelectionButtons(){
        int row = this.CitasTable.getSelectedRow();
        boolean selected = row != -1;
        this.ConsultarCitaButton.setEnabled(selected);
        this.ModificarCitaButton.setEnabled(selected);
        this.BorrarCitaButton.setEnabled(selected);
        boolean pendiente = false;
        if (selected){
            Cita c = this.citasMostradas.get(this.CitasTable.convertRowIndexToModel(row));
            pendiente = c.getEstado() == EstadoCita.NO_CONFIRMADA;
        }
        this.ConfirmarCitaButton.setEnabled(pendiente);
    }

    private void mostrarCitas(ArrayList<Cita> citas){
        this.CitasTable.clearSelection();
        this.CitasTableModel.setRowCount(0);
        this.citasMostradas = new ArrayList<Cita>(citas);
        this.citasMostradas.sort(Comparator.comparing(Cita::getFechaHora));
        for (Cita c : this.citasMostradas){
            this.CitasTableModel.addRow(new Object[]{c.getFechaHora().toLocalDate(), c.getFechaHora().toLocalTime(), c.getCliente().getIdCliente(), c.getCliente().getNombre(), c.getServicio().getNombre(), c.getEstado()});
        }
        this.updateSelectionButtons();
    }

    private void refreshCitasTable() throws Exception {
        ArrayList<Cita> citas;
        if (this.mostrandoPendientes){
            // El metodo de control lanza una excepcion si no hay pendientes.
            LocalDate manana = LocalDate.now().plusDays(1);
            boolean hayPendientes = false;
            for (Cita c : Barberia.getInstance().getCalendarioCitas(manana, manana)){
                if (c.getEstado() == EstadoCita.NO_CONFIRMADA){ hayPendientes = true; }
            }
            citas = hayPendientes ? Barberia.getInstance().enviarNotificacionesConfirmacion() : new ArrayList<Cita>();
            this.VistaLabel.setText("Citas sin confirmar de mañana (consulta, no envia correos)");
        }
        else {
            LocalDate desde = LocalDate.parse(this.DesdeField.getText().trim());
            LocalDate hasta = LocalDate.parse(this.HastaField.getText().trim());
            if (desde.isAfter(hasta)){
                throw new Exception("La fecha inicial no puede ser posterior a la final.");
            }
            citas = Barberia.getInstance().getCalendarioCitas(desde, hasta);
            this.VistaLabel.setText("Calendario: " + desde + " a " + hasta);
        }
        this.mostrarCitas(citas);
    }

    @Override
    public void onDisplay(){
        try{ this.refreshCitasTable(); }
        catch (DateTimeParseException exc){ showBasicError(new Exception("Ingrese fechas validas con formato aaaa-MM-dd.")); }
        catch (Exception exc){ showBasicError(exc); }
    }

    private void connectButtonsActionEvents(){
        this.ConsultarCalendarioButton.addActionListener(e -> {
            this.mostrandoPendientes = false;
            this.onDisplay();
        });
        this.PendientesButton.addActionListener(e -> {
            this.mostrandoPendientes = true;
            this.onDisplay();
        });
        this.CrearCitaButton.addActionListener(e -> {
            new CitaDialog(this.getRootFrame(), null).setVisible(true);
            this.onDisplay();
        });
        this.ModificarCitaButton.addActionListener(e -> {
            try{
                new CitaDialog(this.getRootFrame(), this.getSelectedCita()).setVisible(true);
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.ConsultarCitaButton.addActionListener(e -> {
            try{
                Cita c = this.getSelectedCita();
                String detalle = "Fecha: " + c.getFechaHora().toLocalDate() + "\nHora: " + c.getFechaHora().toLocalTime()
                        + "\nCliente: " + c.getCliente().getIdCliente() + " - " + c.getCliente().getNombre()
                        + "\nTelefono: " + c.getCliente().getTelefono() + "\nCorreo: " + c.getCliente().getEmail()
                        + "\nServicio: " + c.getServicio().getNombre() + "\nEstado: " + c.getEstado();
                JOptionPane.showMessageDialog(this, detalle, "Consulta de Cita", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.BorrarCitaButton.addActionListener(e -> {
            try{
                Cita c = this.getSelectedCita();
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Cancelar la cita de " + c.getCliente().getNombre() + " del " + c.getFechaHora() + "?", "Cancelar Cita", JOptionPane.YES_NO_OPTION);
                if (respuesta != JOptionPane.YES_OPTION){ return; }
                Barberia.getInstance().borrarCita(c.getFechaHora());
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.ConfirmarCitaButton.addActionListener(e -> {
            try{
                Cita c = this.getSelectedCita();
                Barberia.getInstance().confirmarCita(c.getFechaHora());
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
    }
}
