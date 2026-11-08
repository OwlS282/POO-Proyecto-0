package GUI;

import Control.Barberia;
import Logica.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;

public class ServicesPanel extends ContentPanel {
    private JTable ServiciosTable;
    private DefaultTableModel ServiciosTableModel;
    private JButton CrearServicioButton;
    private JButton ModificarServicioButton;
    private JButton BorrarServicioButton;
    private JButton ConsultarServicioButton;

    public ServicesPanel(){
        this.setLayout(new BorderLayout(0, 0));
        JPanel TopButtonsPanel = new JPanel();
        this.add(TopButtonsPanel, BorderLayout.NORTH);
        this.CrearServicioButton = new JButton("Crear Servicio");
        this.ModificarServicioButton = new JButton("Modificar Servicio");
        this.BorrarServicioButton = new JButton("Borrar Servicio");
        this.ConsultarServicioButton = new JButton("Consultar Servicio");
        TopButtonsPanel.add(this.CrearServicioButton);
        TopButtonsPanel.add(this.ModificarServicioButton);
        TopButtonsPanel.add(this.BorrarServicioButton);
        TopButtonsPanel.add(this.ConsultarServicioButton);

        this.ServiciosTableModel = new DefaultTableModel(new Object[]{"Nombre"}, 0);
        this.ServiciosTable = new JTable(this.ServiciosTableModel);
        this.ServiciosTable.setDefaultEditor(Object.class, null);
        this.ServiciosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ServiciosTable.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = this.ServiciosTable.getSelectedRow() != -1;
            this.ModificarServicioButton.setEnabled(selected);
            this.BorrarServicioButton.setEnabled(selected);
            this.ConsultarServicioButton.setEnabled(selected);
        });
        this.ModificarServicioButton.setEnabled(false);
        this.BorrarServicioButton.setEnabled(false);
        this.ConsultarServicioButton.setEnabled(false);
        this.add(new JScrollPane(this.ServiciosTable), BorderLayout.CENTER);
        this.connectButtonsActionEvents();
    }

    private String getSelectedServicio() throws Exception {
        int row = this.ServiciosTable.getSelectedRow();
        if (row == -1){
            throw new Exception("Seleccione un servicio.");
        }
        return (String) this.ServiciosTableModel.getValueAt(this.ServiciosTable.convertRowIndexToModel(row), 0);
    }

    @Override
    public void onDisplay(){
        this.ServiciosTableModel.setRowCount(0);
        ArrayList<Servicio> servicios = Barberia.getInstance().getListaServicio();
        servicios.sort(Comparator.comparing(Servicio::getNombre));
        for (Servicio s : servicios){
            this.ServiciosTableModel.addRow(new Object[]{s.getNombre()});
        }
    }

    private void connectButtonsActionEvents(){
        this.CrearServicioButton.addActionListener(e -> {
            new ServicioDialog(this.getRoot(), null).setVisible(true);
            this.onDisplay();
        });
        this.ModificarServicioButton.addActionListener(e -> {
            try{
                Servicio s = Barberia.getInstance().consultarServicio(this.getSelectedServicio());
                new ServicioDialog(this.getRoot(), s).setVisible(true);
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.ConsultarServicioButton.addActionListener(e -> {
            try{
                Servicio s = Barberia.getInstance().consultarServicio(this.getSelectedServicio());
                String uso = Barberia.getInstance().servicioEnUso(s) ? "Si" : "No";
                JOptionPane.showMessageDialog(this, "Nombre: " + s.getNombre() + "\nUtilizado en citas: " + uso, "Consulta de Servicio", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.BorrarServicioButton.addActionListener(e -> {
            try{
                String nombre = this.getSelectedServicio();
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Borrar el servicio " + nombre + "?", "Borrar Servicio", JOptionPane.YES_NO_OPTION);
                if (respuesta != JOptionPane.YES_OPTION){ return; }
                Barberia.getInstance().borrarServicio(nombre);
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
    }
}
