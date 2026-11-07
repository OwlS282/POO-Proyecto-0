package GUI;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;

public class ControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel MainPanel;
	private CardLayout cardLayout;
	private JPanel contentsPanel;

	private ClientsPanel clientsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlPanel frame = new ControlPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ControlPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		MainPanel = new JPanel();
		MainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(MainPanel);
		MainPanel.setLayout(new BorderLayout(0, 0));

		this.contentsPanel = new JPanel();
		this.cardLayout = new CardLayout(0, 0);
		this.contentsPanel.setLayout(this.cardLayout);

		MainPanel.add(this.contentsPanel, BorderLayout.CENTER);

		/// EJEMPLO: Hacer lo mismo con otros XPanel()
		this.clientsPanel = new ClientsPanel();
		this.contentsPanel.add(clientsPanel, "CLIENTES");
		this.showContent(this.clientsPanel, "CLIENTES");
		/// FIN EJEMPLO

		JPanel SidebarPanel = new JPanel();
		MainPanel.add(SidebarPanel, BorderLayout.WEST);
		SidebarPanel.setLayout(new BoxLayout(SidebarPanel, BoxLayout.Y_AXIS));

		/// EJEMPLO: Ver contenidos the ClientsPanel para dar una idea
		JButton ClientesButton = new JButton("Clientes");
		ClientesButton.addActionListener(e -> {
			this.showContent(this.clientsPanel, "CLIENTES");
		});
		SidebarPanel.add(ClientesButton);
		/// FIN EJEMPLO

		JButton CitasButton = new JButton("Citas");
		// conectar evento
		SidebarPanel.add(CitasButton);

		JButton ListaEsperaButton = new JButton("Espera");
		// conectar evento
		SidebarPanel.add(ListaEsperaButton);

		JButton AdminButton = new JButton("Admin");
		// conectar evento
		SidebarPanel.add(AdminButton);
	}

	private void showContent(ContentPanel content, String name){
		this.cardLayout.show(this.contentsPanel, name);
		content.onDisplay();
	}
}
