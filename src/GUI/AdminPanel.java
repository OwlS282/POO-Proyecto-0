package GUI;

import javax.swing.*;
import java.awt.BorderLayout;

public class AdminPanel extends ContentPanel {
    private JTabbedPane AdminTabs;
    private ServicesPanel servicesPanel;
    private HorariosPanel horariosPanel;

    public AdminPanel(){
        this.setLayout(new BorderLayout(0, 0));
        this.servicesPanel = new ServicesPanel();
        this.horariosPanel = new HorariosPanel();
        this.AdminTabs = new JTabbedPane();
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
