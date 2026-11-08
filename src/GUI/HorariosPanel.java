package GUI;

import Control.Barberia;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class HorariosPanel extends ContentPanel {
    private JComboBox<DayOfWeek> DiaComboBox;
    private JTextField HoraInicioField;
    private JTextField HoraFinField;
    private JTextField FechaConsultaField;
    private JTextField HoraConsultaField;
    private JButton GuardarHorarioButton;
    private JButton ConsultarHorarioButton;

    public HorariosPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.DiaComboBox = new JComboBox<>(DayOfWeek.values());
        this.DiaComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus){
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof DayOfWeek){
                    this.setText(((DayOfWeek) value).getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.forLanguageTag("es")));
                }
                return this;
            }
        });
        this.HoraInicioField = new JTextField("08:00", 8);
        this.HoraFinField = new JTextField("18:00", 8);
        this.GuardarHorarioButton = new JButton("Guardar Horario");
        GuardarHorarioButton.setForeground(new Color(0, 64, 128));
        GuardarHorarioButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
        GuardarHorarioButton.setBackground(new Color(255, 255, 255));
        JPanel ConfiguracionPanel = new JPanel();
        ConfiguracionPanel.setBackground(new Color(255, 255, 255));
        JLabel label = new JLabel("Dia:");
        label.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        label.setForeground(new Color(0, 0, 64));
        ConfiguracionPanel.add(label);
        ConfiguracionPanel.add(this.DiaComboBox);
        JLabel label_1 = new JLabel("Inicio (HH:mm):");
        label_1.setBackground(new Color(0, 0, 64));
        label_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        label_1.setForeground(new Color(0, 0, 64));
        ConfiguracionPanel.add(label_1);
        ConfiguracionPanel.add(this.HoraInicioField);
        JLabel label_2 = new JLabel("Fin (HH:mm):");
        label_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        label_2.setForeground(new Color(0, 0, 64));
        label_2.setBackground(new Color(255, 255, 255));
        ConfiguracionPanel.add(label_2);
        ConfiguracionPanel.add(this.HoraFinField);
        ConfiguracionPanel.add(this.GuardarHorarioButton);
        this.add(ConfiguracionPanel);
        JLabel label_5 = new JLabel("Guardar reemplaza el horario de ese dia. Los campos son una propuesta, no el horario guardado.");
        label_5.setFont(new Font("Lucida Fax", Font.BOLD | Font.ITALIC, 13));
        label_5.setForeground(new Color(0, 0, 64));
        this.add(label_5);

        this.FechaConsultaField = new JTextField(LocalDate.now().toString(), 10);
        this.HoraConsultaField = new JTextField("10:00", 8);
        this.ConsultarHorarioButton = new JButton("Comprobar Atencion");
        ConsultarHorarioButton.setForeground(new Color(159, 32, 32));
        ConsultarHorarioButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
        ConsultarHorarioButton.setBackground(new Color(255, 255, 255));
        JPanel ConsultaPanel = new JPanel();
        ConsultaPanel.setBackground(new Color(255, 255, 255));
        JLabel label_3 = new JLabel("Fecha (aaaa-MM-dd):");
        label_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        label_3.setForeground(new Color(128, 0, 0));
        ConsultaPanel.add(label_3);
        ConsultaPanel.add(this.FechaConsultaField);
        JLabel label_4 = new JLabel("Hora (HH:mm):");
        label_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        label_4.setForeground(new Color(128, 0, 0));
        ConsultaPanel.add(label_4);
        ConsultaPanel.add(this.HoraConsultaField);
        ConsultaPanel.add(this.ConsultarHorarioButton);
        this.add(ConsultaPanel);
        JLabel label_6 = new JLabel("Esta consulta comprueba el horario; no indica si ya existe una cita a esa hora.");
        label_6.setForeground(new Color(64, 0, 0));
        label_6.setFont(new Font("Lucida Fax", Font.BOLD | Font.ITALIC, 13));
        this.add(label_6);
        this.connectButtonsActionEvents();
    }

    @Override
    public void onDisplay(){
        // Los horarios guardados no tienen un getter en la capa de control.
    }

    private void connectButtonsActionEvents(){
        this.GuardarHorarioButton.addActionListener(e -> {
            try{
                DayOfWeek dia = (DayOfWeek) this.DiaComboBox.getSelectedItem();
                LocalTime inicio = LocalTime.parse(this.HoraInicioField.getText().trim());
                LocalTime fin = LocalTime.parse(this.HoraFinField.getText().trim());
                if (fin.isBefore(inicio)){
                    throw new Exception("La hora final no puede ser anterior a la inicial.");
                }
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Establecer este horario para el dia seleccionado?", "Guardar Horario", JOptionPane.YES_NO_OPTION);
                if (respuesta != JOptionPane.YES_OPTION){ return; }
                Barberia.getInstance().establecerHorario(dia, inicio, fin);
                JOptionPane.showMessageDialog(this, "Horario establecido.");
            }
            catch (DateTimeParseException exc){ showBasicError(new Exception("Ingrese horas validas con formato HH:mm.")); }
            catch (Exception exc){ showBasicError(exc); }
        });
        this.ConsultarHorarioButton.addActionListener(e -> {
            try{
                LocalDate fecha = LocalDate.parse(this.FechaConsultaField.getText().trim());
                LocalTime hora = LocalTime.parse(this.HoraConsultaField.getText().trim());
                boolean enHorario = Barberia.getInstance().enHorarioDeAtencion(LocalDateTime.of(fecha, hora));
                JOptionPane.showMessageDialog(this, enHorario ? "Dentro del horario de atencion." : "Fuera del horario de atencion o dia sin configurar.");
            }
            catch (DateTimeParseException exc){ showBasicError(new Exception("Ingrese una fecha aaaa-MM-dd y una hora HH:mm validas.")); }
            catch (Exception exc){ showBasicError(exc); }
        });
    }
}
