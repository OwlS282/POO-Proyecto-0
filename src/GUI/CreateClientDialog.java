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
Clase: CreateClientDialog
	Diálogo para registrar un cliente nuevo. Solicita nombre, teléfono y correo, y
	los envía a Barberia. Si los datos no son válidos, muestra el error y deja
	el diálogo abierto para corregirlos.
*/
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
