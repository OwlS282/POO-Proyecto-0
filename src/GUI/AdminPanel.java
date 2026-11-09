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
Clase: AdminPanel
	Sección de administración que agrupa en pestañas la gestión de tipos de
	servicio y el horario de atención. Actualiza la pestaña visible al cambiar de una a otra.
*/

package GUI;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;

public class AdminPanel extends ContentPanel {
    private JTabbedPane AdminTabs;
    private ServicesPanel servicesPanel;
    private HorariosPanel horariosPanel;

    public AdminPanel(){
        this.setLayout(new BorderLayout(0, 0));
        this.servicesPanel = new ServicesPanel();
        this.horariosPanel = new HorariosPanel();
        this.AdminTabs = new JTabbedPane();
        AdminTabs.setForeground(new Color(255, 255, 255));
        AdminTabs.setBackground(new Color(64, 0, 0));
        this.AdminTabs.addTab("Servicios", this.servicesPanel);
        this.AdminTabs.addTab("Horarios", this.horariosPanel);
        this.AdminTabs.addChangeListener(e -> this.onDisplay());
        this.add(this.AdminTabs, BorderLayout.CENTER);
    }

    @Override
    public void onDisplay(){
        if (this.AdminTabs.getSelectedComponent() == this.servicesPanel){
            this.servicesPanel.onDisplay();
        }
        else {
            this.horariosPanel.onDisplay();
        }
    }
}
