package GUI;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Control.Barberia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel MainPanel;
	private CardLayout cardLayout;
	private JPanel contentsPanel;

	private ClientsPanel clientsPanel;
	private CitasPanel citasPanel;
	private EsperaPanel esperaPanel;
	private AdminPanel adminPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Barberia.getInstance().cargarDatosDisco();
					ControlPanel frame = new ControlPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "No se pudo iniciar la aplicacion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ControlPanel() {
		setBackground(new Color(0, 0, 64));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				try{
					Barberia.getInstance().guardarDatosDisco();
					dispose();
				}
				catch (Exception exc){
					JOptionPane.showMessageDialog(ControlPanel.this, "No se pudieron guardar los datos: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setBounds(100, 100, 1100, 650);
		setMinimumSize(new java.awt.Dimension(1050, 550));
		setTitle("Barberia");
		MainPanel = new JPanel();
		MainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(MainPanel);
		MainPanel.setLayout(new BorderLayout(0, 0));

		this.contentsPanel = new JPanel();
		this.cardLayout = new CardLayout(0, 0);
		this.contentsPanel.setLayout(this.cardLayout);

		MainPanel.add(this.contentsPanel, BorderLayout.CENTER);

		this.clientsPanel = new ClientsPanel();
		clientsPanel.setBackground(new Color(159, 32, 32));
		clientsPanel.setForeground(new Color(159, 32, 32));
		this.citasPanel = new CitasPanel();
		this.esperaPanel = new EsperaPanel();
		this.adminPanel = new AdminPanel();
		this.contentsPanel.add(clientsPanel, "CLIENTES");
		this.contentsPanel.add(citasPanel, "CITAS");
		this.contentsPanel.add(esperaPanel, "ESPERA");
		this.contentsPanel.add(adminPanel, "ADMIN");
		this.showContent(this.clientsPanel, "CLIENTES");

		JPanel SidebarPanel = new JPanel();
		MainPanel.add(SidebarPanel, BorderLayout.WEST);
		SidebarPanel.setLayout(new BoxLayout(SidebarPanel, BoxLayout.Y_AXIS));

		JButton ClientesButton = new JButton("Clientes");
		ClientesButton.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		ClientesButton.setForeground(new Color(255, 255, 255));
		ClientesButton.setBackground(new Color(159, 32, 32));
		ClientesButton.addActionListener(e -> {
			this.showContent(this.clientsPanel, "CLIENTES");
		});
		ClientesButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		SidebarPanel.add(ClientesButton);
		

		JButton CitasButton = new JButton("Citas");
		CitasButton.setForeground(new Color(255, 255, 255));
		CitasButton.setBackground(new Color(34, 145, 255));
		CitasButton.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		CitasButton.addActionListener(e -> {
			this.showContent(this.citasPanel, "CITAS");
		});
		CitasButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		SidebarPanel.add(CitasButton);

		JButton ListaEsperaButton = new JButton("Espera");
		ListaEsperaButton.setForeground(new Color(255, 255, 255));
		ListaEsperaButton.setBackground(new Color(159, 32, 32));
		ListaEsperaButton.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		ListaEsperaButton.addActionListener(e -> {
			this.showContent(this.esperaPanel, "ESPERA");
		});
		ListaEsperaButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		SidebarPanel.add(ListaEsperaButton);

		JButton AdminButton = new JButton("Admin");
		AdminButton.setForeground(new Color(255, 255, 255));
		AdminButton.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		AdminButton.setBackground(new Color(34, 145, 255));
		AdminButton.addActionListener(e -> {
			this.showContent(this.adminPanel, "ADMIN");
		});
		AdminButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		SidebarPanel.add(AdminButton);
	}

	private void showContent(ContentPanel content, String name){
		this.cardLayout.show(this.contentsPanel, name);
		content.onDisplay();
	}
}
