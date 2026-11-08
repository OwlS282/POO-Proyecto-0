package GUI;

import Control.Barberia;
import Logica.Servicio;
import javax.swing.*;

public class ServicioDialog extends ContentDialog {
    private JTextField NombreField;
    private Servicio servicio;
    private String oldNombre;

    public ServicioDialog(JFrame parent, Servicio servicio){
        super(parent, servicio == null ? "Crear Servicio" : "Modificar Servicio", true);
        this.servicio = servicio;
        this.oldNombre = servicio == null ? null : servicio.getNombre();
        this.initializeDialog();
    }

    @Override
    protected void initialize(){
        this.NombreField = new JTextField(25);
        if (this.servicio != null){
            this.NombreField.setText(this.servicio.getNombre());
        }
        this.Contents.add(new JLabel("Nombre:"));
        this.Contents.add(this.NombreField);
        this.Contents.add(Box.createVerticalStrut(10));
    }

    @Override
    protected void onConfirm(){
        try{
            String nombre = this.NombreField.getText().trim();
            if (nombre.isEmpty()){
                throw new Exception("Ingrese el nombre del servicio.");
            }
            if (this.servicio == null){
                Barberia.getInstance().crearServicio(nombre);
            }
            else {
                Barberia.getInstance().modificarServicio(this.oldNombre, nombre);
            }
            this.dispose();
        }
        catch (Exception exc){
            this.showErrorMessage("Error al guardar servicio", exc);
        }
    }
}
