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
Clase: ModificarClientDialog
	Diálogo para editar los datos de un cliente existente. Muestra sus datos
	actuales en los campos y, al confirmar, los envía a Barberia. Si los datos
	no son válidos, muestra el error y no cierra el diálogo.
*/


package GUI;

import javax.swing.*;

import Control.Barberia;
import Logica.Cliente;

public class ModificarClientDialog extends ContentDialog {
    private JTextField TelefonoField;
    private JTextField EmailField;
    private JTextField NombreField;
    private Cliente cliente;

    public ModificarClientDialog(JFrame parent, Cliente cliente){
        super(parent, "Modificar Cliente", true);
        this.cliente = cliente;
        this.initializeDialog();
    }

    @Override
    protected void initialize(){
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

        if (this.cliente != null){
            this.NombreField.setText(this.cliente.getNombre());
            this.TelefonoField.setText(this.cliente.getTelefono());
            this.EmailField.setText(this.cliente.getEmail());
        }
    }

    @Override
    protected void onConfirm() {
        try{
            Barberia.getInstance().modificarCliente(
                    cliente.getIdCliente(), this.NombreField.getText(), this.TelefonoField.getText(), this.EmailField.getText()
            );

            this.dispose();
        }
        catch (Exception exc){
            this.showErrorMessage("Error al modificar cliente", exc);
        }
    }
}
