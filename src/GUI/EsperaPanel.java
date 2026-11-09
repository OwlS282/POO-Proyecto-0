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
Clase: EsperaPanel
	Sección de la lista de espera. Muestra los clientes en orden de llegada con
	su posición, y permite agregar un cliente al final, retirar al primero o
	retirar uno seleccionado.
*/

package GUI;

import Control.Barberia;
import Logica.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class EsperaPanel extends ContentPanel {
    private JTable EsperaTable;
    private DefaultTableModel EsperaTableModel;
    private JButton AgregarClienteButton;
    private JButton RetirarPrimeroButton;
    private JButton RetirarSeleccionadoButton;

    public EsperaPanel(){
        this.setLayout(new BorderLayout(0, 0));
        JPanel TopButtonsPanel = new JPanel();
        this.AgregarClienteButton = new JButton("Agregar Cliente");
        this.RetirarPrimeroButton = new JButton("Retirar Primero");
        this.RetirarSeleccionadoButton = new JButton("Retirar Seleccionado");
        this.RetirarPrimeroButton.setEnabled(false);
        this.RetirarSeleccionadoButton.setEnabled(false);
        TopButtonsPanel.add(this.AgregarClienteButton);
        TopButtonsPanel.add(this.RetirarPrimeroButton);
        TopButtonsPanel.add(this.RetirarSeleccionadoButton);
        this.add(TopButtonsPanel, BorderLayout.NORTH);
        this.EsperaTableModel = new DefaultTableModel(new Object[]{"Posicion", "ID", "Nombre", "Telefono"}, 0);
        this.EsperaTable = new JTable(this.EsperaTableModel);
        this.EsperaTable.setDefaultEditor(Object.class, null);
        this.EsperaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.EsperaTable.getSelectionModel().addListSelectionListener(e -> {
            this.RetirarSeleccionadoButton.setEnabled(this.EsperaTable.getSelectedRow() != -1);
        });
        this.add(new JScrollPane(this.EsperaTable), BorderLayout.CENTER);
        this.connectButtonsActionEvents();
    }

    @Override
    public void onDisplay(){
        this.EsperaTableModel.setRowCount(0);
        int posicion = 1;
        for (Cliente c : Barberia.getInstance().getListaEspera()){
            this.EsperaTableModel.addRow(new Object[]{posicion++, c.getIdCliente(), c.getNombre(), c.getTelefono()});
        }
        this.RetirarPrimeroButton.setEnabled(this.EsperaTableModel.getRowCount() > 0);
    }

    private void connectButtonsActionEvents(){
        this.AgregarClienteButton.addActionListener(e -> {
            new AgregarEsperaDialog(this.getRootFrame()).setVisible(true);
            this.onDisplay();
        });
        this.RetirarPrimeroButton.addActionListener(e -> {
            try{
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Retirar al primero de la lista de espera?", "Retirar Cliente", JOptionPane.YES_NO_OPTION);
                if (respuesta != JOptionPane.YES_OPTION){ return; }
                if (!Barberia.getInstance().borrarClienteListaEspera()){
                    throw new Exception("La lista de espera esta vacia.");
                }
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.RetirarSeleccionadoButton.addActionListener(e -> {
            try{
                int row = this.EsperaTable.getSelectedRow();
                if (row == -1){ throw new Exception("Seleccione un cliente."); }
                row = this.EsperaTable.convertRowIndexToModel(row);
                String id = (String) this.EsperaTableModel.getValueAt(row, 1);
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Retirar al cliente seleccionado de espera?", "Retirar Cliente", JOptionPane.YES_NO_OPTION);
                if (respuesta != JOptionPane.YES_OPTION){ return; }
                if (!Barberia.getInstance().borrarClienteListaEspera(id)){
                    throw new Exception("El cliente ya no esta en la lista de espera.");
                }
                this.onDisplay();
            }
            catch (Exception exc){ showBasicError(exc); }
        });
    }
}
