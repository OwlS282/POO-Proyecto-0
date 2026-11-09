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
Clase: HorariosPanel
	Pestaña del horario de atención. Permite elegir un día de la semana y
	establecer su hora de inicio y de fin. También incluye una consulta para
	comprobar si una fecha y hora están dentro del horario de atención.
*/

package GUI;

import Control.Barberia;
import Logica.Horario;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.*;

public class HorariosPanel extends ContentPanel {
    private JComboBox<DayOfWeek> DiaComboBox;
    private JTextField HoraInicioField;
    private JTextField HoraFinField;
    private JTextField FechaConsultaField;
    private JTextField HoraConsultaField;
    private JButton GuardarHorarioButton;
    private JButton ConsultarHorarioButton;
    private JLabel HorarioActualLabel;

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
        JPanel ConfiguracionPanel = new JPanel();
        ConfiguracionPanel.add(new JLabel("Dia:"));
        ConfiguracionPanel.add(this.DiaComboBox);
        ConfiguracionPanel.add(new JLabel("Inicio (HH:mm):"));
        ConfiguracionPanel.add(this.HoraInicioField);
        ConfiguracionPanel.add(new JLabel("Fin (HH:mm):"));
        ConfiguracionPanel.add(this.HoraFinField);
        ConfiguracionPanel.add(this.GuardarHorarioButton);
        this.add(ConfiguracionPanel);
        this.HorarioActualLabel = new JLabel(" ");
        this.add(this.HorarioActualLabel);
        this.add(new JLabel("Guardar reemplaza el horario de ese dia. Los campos son una propuesta, no el horario guardado."));

        this.FechaConsultaField = new JTextField(LocalDate.now().toString(), 10);
        this.HoraConsultaField = new JTextField("10:00", 8);
        this.ConsultarHorarioButton = new JButton("Comprobar Atencion");
        JPanel ConsultaPanel = new JPanel();
        ConsultaPanel.add(new JLabel("Fecha (aaaa-MM-dd):"));
        ConsultaPanel.add(this.FechaConsultaField);
        ConsultaPanel.add(new JLabel("Hora (HH:mm):"));
        ConsultaPanel.add(this.HoraConsultaField);
        ConsultaPanel.add(this.ConsultarHorarioButton);
        this.add(ConsultaPanel);
        this.add(new JLabel("Esta consulta comprueba el horario; no indica si ya existe una cita a esa hora."));
        this.DiaComboBox.addActionListener(e -> this.cargarHorarioDelDia());
        this.connectButtonsActionEvents();
        
        this.HorarioActualLabel = new JLabel(" ");
        this.add(this.HorarioActualLabel);
    }

    @Override
    public void onDisplay(){
        this.cargarHorarioDelDia();
    }

    private void cargarHorarioDelDia(){
        DayOfWeek dia = (DayOfWeek) this.DiaComboBox.getSelectedItem();
        Horario h = Barberia.getInstance().getHorario(dia);
        if (h == null){
            this.HorarioActualLabel.setText("Este dia no tiene horario de atencion guardado.");
        }
        else {
            this.HoraInicioField.setText(h.getHoraInicio().toString());
            this.HoraFinField.setText(h.getHoraFin().toString());
            this.HorarioActualLabel.setText("Horario guardado: " + h.getHoraInicio() + " a " + h.getHoraFin());
        }
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
                this.cargarHorarioDelDia();
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
