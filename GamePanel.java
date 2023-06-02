import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    public MainFrame container;
    private JLabel statusbar;

    protected GamePanel(MainFrame c) {
        container = c;
        statusbar = new JLabel("default");
        // STATUS BAR is for debugging --- remove when turn in
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    protected GamePanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    protected void navigateTo(String loc) {
        container.layout.show(container.mainCards, loc);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        statusbar.setText("X: " + e.getX() + "  Y: " + e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
