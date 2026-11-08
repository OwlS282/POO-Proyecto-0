package GUI;

import Control.Barberia;
import Logica.Cliente;
import javax.swing.*;

public class AgregarEsperaDialog extends ContentDialog {
    private JComboBox<Cliente> ClienteComboBox;

    public AgregarEsperaDialog(JFrame parent){
        super(parent, "Agregar Cliente a Espera", true);
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
        this.Contents.add(new JLabel("Cliente registrado:"));
        this.Contents.add(this.ClienteComboBox);
        if (this.ClienteComboBox.getItemCount() == 0){
            this.Contents.add(new JLabel("Primero debe registrar un cliente en Clientes."));
            this.ConfirmarButton.setEnabled(false);
        }
    }

    @Override
    protected void onConfirm(){
        try{
            Cliente c = (Cliente) this.ClienteComboBox.getSelectedItem();
            if (c == null){ throw new Exception("Seleccione un cliente."); }
            if (!Barberia.getInstance().agregarClienteListaEspera(c.getIdCliente())){
                throw new Exception("El cliente ya esta en la lista de espera.");
            }
            this.dispose();
        }
        catch (Exception exc){ this.showErrorMessage("Error al agregar a espera", exc); }
    }
}
