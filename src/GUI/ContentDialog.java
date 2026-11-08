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
