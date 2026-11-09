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
Clase: ContentPanel
	Clase abstracta base de las secciones de la interfaz. Ofrece métodos comunes
	(obtener la ventana principal y mostrar mensajes de error) y obliga a cada
	sección a definir onDisplay(), que actualiza su contenido cada vez que se muestra.
*/

package GUI;

import javax.swing.*;

public abstract class ContentPanel extends JPanel {
    protected JFrame getRootFrame(){
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    protected void showBasicError(Exception exc){
        JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    public abstract void onDisplay();
}
