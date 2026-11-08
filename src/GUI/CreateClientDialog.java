package GUI;

import Control.Barberia;

import javax.swing.*;

public class CreateClientDialog extends ContentDialog {
    private JTextField TelefonoField;
    private JTextField EmailField;
    private JTextField NombreField;

    public CreateClientDialog(JFrame parent){
        super(parent, "Crear Cliente", true);
        this.initializeDialog();
    }

    @Override
    protected void initialize() {
        this.TelefonoField = new JTextField(20);
        this.EmailField = new JTextField(20);
        this.NombreField = new JTextField(20);

        this.Contents.add(new JLabel("Nombre: "));
        this.Contents.add(this.NombreField);
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Telefono: "));
        this.Contents.add(this.TelefonoField);
        this.Contents.add(Box.createVerticalStrut(10));
        this.Contents.add(new JLabel("Email: "));
        this.Contents.add(this.EmailField);
        this.Contents.add(Box.createVerticalStrut(10));
    }

    @Override
    protected void onConfirm() {
        try{
            Barberia.getInstance().crearCliente(
              this.NombreField.getText(), this.TelefonoField.getText(), this.EmailField.getText()
            );

            this.dispose();
        }
        catch (Exception exc){
            this.showErrorMessage("Error al crear cliente", exc);
        }
    }
}
