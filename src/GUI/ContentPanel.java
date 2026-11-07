package GUI;

import javax.swing.*;

public abstract class ContentPanel extends JPanel {
    protected final JFrame root = (JFrame) SwingUtilities.getWindowAncestor(this);

    protected void showBasicError(Exception exc){
        JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    public abstract void onDisplay();
}
