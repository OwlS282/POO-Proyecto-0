package GUI;

import Control.Barberia;
import Logica.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class ClientsPanel extends ContentPanel {
    private JPanel TopButtonsPanel;
    private JButton CrearClienteButton;
    private JButton ModicarClienteButton;
    private JButton BorrarClienteButton;
    private JButton ConsultarClienteButton;

    private JScrollPane ClienteScrollPane;
    private JTable ClientesTable;
    private DefaultTableModel ClientesTableModel;

    public ClientsPanel(){
        this.setLayout(new BorderLayout(0, 0));

        this.TopButtonsPanel = new JPanel();
        this.add(this.TopButtonsPanel, BorderLayout.NORTH);

        this.CrearClienteButton = new JButton("Crear Cliente");
        this.ModicarClienteButton = new JButton("Modificar Cliente");
        this.ModicarClienteButton.setEnabled(false);
        this.BorrarClienteButton = new JButton("Borrar Cliente");
        this.BorrarClienteButton.setEnabled(false);
        this.ConsultarClienteButton = new JButton("Consultar Cliente");
        this.ConsultarClienteButton.setEnabled(false);

        this.TopButtonsPanel.add(this.CrearClienteButton);
        this.TopButtonsPanel.add(this.ModicarClienteButton);
        this.TopButtonsPanel.add(this.BorrarClienteButton);
        this.TopButtonsPanel.add(this.ConsultarClienteButton);

        this.connectButtonsActionEvents();

        // una tabla cualquiera. Hacer lo mismo para Citas, Servicio, etc.
        this.ClienteScrollPane = new JScrollPane();
        this.add(this.ClienteScrollPane, BorderLayout.CENTER);
        this.ClientesTable = new JTable();
        this.ClientesTable.setDefaultEditor(Object.class, null);  // disabling editing
        this.ClientesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ClientesTableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Telefono", "Email"}, 0);
        this.ClientesTable.setModel(this.ClientesTableModel);
        this.ClientesTable.getSelectionModel().addListSelectionListener(e ->{
            if (e.getValueIsAdjusting()) { return; }

            boolean clientSelected = this.ClientesTable.getSelectedRow() != -1;
            ModicarClienteButton.setEnabled(clientSelected);
            BorrarClienteButton.setEnabled(clientSelected);
            ConsultarClienteButton.setEnabled(clientSelected);
        });
        this.ClienteScrollPane.setViewportView(this.ClientesTable);
    }

    String getSelectedClientId() throws Exception{
        int selectedRow = ClientesTable.getSelectedRow();
        if (selectedRow == -1){
            return null;
        }

        String id = String.valueOf(this.ClientesTableModel.getValueAt(selectedRow, 0));
        return String.valueOf(this.ClientesTableModel.getValueAt(selectedRow, 0));
    }

    @Override
    public void onDisplay(){
        this.refreshClientsTable();
    }

    public void refreshClientsTable(){
        this.ClientesTableModel.setRowCount(0);
        ArrayList<Cliente> clientes = Barberia.getInstance().getListaClientes();

        for (Cliente c : clientes){
            Object[] row = new Object[] {c.getIdCliente(), c.getNombre(), c.getTelefono(), c.getEmail()};
            this.ClientesTableModel.addRow(row);
        }
    }

    private void connectButtonsActionEvents(){
        this.CrearClienteButton.addActionListener(e -> {
            try{
                ContentDialog dialog = new CreateClientDialog(this.getRootFrame());
                dialog.setVisible(true);
                this.onDisplay();
            }
            catch (Exception exc){
                showBasicError(exc);
            }
        });

        this.BorrarClienteButton.addActionListener(e ->{
            try{
                String c = this.getSelectedClientId();
                Barberia.getInstance().borrarCliente(c);
                this.onDisplay();
            }
            catch (Exception exc){
                showBasicError(exc);
            }
        });

        this.ModicarClienteButton.addActionListener(e -> {
            try{
                ContentDialog dialog = new ModificarClientDialog(this.getRootFrame(), Barberia.getInstance().consultarCliente(this.getSelectedClientId()));
                dialog.setVisible(true);
                this.onDisplay();
            }
            catch (Exception exc){
                showBasicError(exc);
            }
        });

        this.ConsultarClienteButton.addActionListener(e -> {
            try{
                ContentDialog dialog = new ConsultarClientDialog(this.getRootFrame(), Barberia.getInstance().consultarCliente(this.getSelectedClientId()));
                dialog.setVisible(true);
                this.onDisplay();
            }
            catch (Exception exc){
                showBasicError(exc);
            }
        });
    }
}
