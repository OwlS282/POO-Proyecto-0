package GUI;

import javax.swing.*;
import Logica.Cliente;

public class ConsultarClientDialog extends ContentDialog {
    private final Cliente c;

    public ConsultarClientDialog(JFrame parent, Cliente c){
        this.c = c;
        super(parent, "Consulta de Cliente", false);
    }

    @Override
    protected void initialize() {
        this.Contents.add(new JLabel("ID: " + c.getIdCliente()));
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Nombre: " + c.getNombre()));
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Telefono: " + c.getTelefono()));
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Email: " + c.getEmail()));
    }

    @Override
    protected void onConfirm() {
        // nada :)
    }
}
