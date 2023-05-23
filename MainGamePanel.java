import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MainGamePanel extends GamePanel {
    public MainGamePanel(MainFrame c) {
        super(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameUtils.drawImage("StartScreen1.png", g);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        repaint();
    }
}
