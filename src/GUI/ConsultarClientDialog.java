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
Clase: ConsultarClientDialog
	Diálogo de solo lectura que muestra el ID, nombre, teléfono y correo de un
	cliente. No tiene botón de confirmar.
*/


package GUI;

import javax.swing.*;
import Logica.Cliente;

public class ConsultarClientDialog extends ContentDialog {
    private final Cliente c;

    public ConsultarClientDialog(JFrame parent, Cliente c){
        super(parent, "Consulta de Cliente", false);
        this.c = c;
        this.initializeDialog();
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
