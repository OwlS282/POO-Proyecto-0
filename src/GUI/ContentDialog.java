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
Clase: ContentDialog
	Clase abstracta base de todos los diálogos emergentes. Construye la estructura
	común (panel de contenido y botón Confirmar opcional) y muestra mensajes de
	error. Cada diálogo hijo define qué campos muestra (initialize) y qué hace
	al confirmar (onConfirm).
*/


package GUI;

import javax.swing.*;
import java.awt.Component;

public abstract class ContentDialog extends JDialog {
    protected final JPanel MainPanel;
    protected final JPanel Contents;
    protected JButton ConfirmarButton;
    private final JFrame parentFrame;

    public ContentDialog(JFrame parent, String title, boolean confirmButton){
        super(parent, title, true);  // modal; blocks others executions until closed
        this.parentFrame = parent;

        this.MainPanel = new JPanel();
        this.MainPanel.setLayout(new BoxLayout(this.MainPanel, BoxLayout.Y_AXIS));

        this.Contents = new JPanel();
        this.Contents.setLayout(new BoxLayout(this.Contents, BoxLayout.Y_AXIS));
        this.Contents.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        this.MainPanel.add(this.Contents);
        if (confirmButton){
            this.ConfirmarButton = new JButton("Confirmar");
            this.ConfirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.ConfirmarButton.addActionListener(e-> this.onConfirm());
            this.MainPanel.add(this.ConfirmarButton);
            this.MainPanel.add(Box.createVerticalStrut(10));
        }

        this.setContentPane(this.MainPanel);
    }

    protected final void initializeDialog(){
        this.initialize();
        this.pack();
        this.setLocationRelativeTo(this.parentFrame);
    }

    protected void showErrorMessage(String title, Exception exc){
        JOptionPane.showMessageDialog(this, exc.getMessage(), title, JOptionPane.ERROR_MESSAGE);
    }
    protected abstract void initialize();
    protected abstract void onConfirm();
}
