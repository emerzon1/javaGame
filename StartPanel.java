import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.*;

import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartPanel extends JPanel {

    public StartPanel() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("StartScreen1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, null);
    }
}
