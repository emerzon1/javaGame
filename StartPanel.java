import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
public class StartPanel extends JPanel {

    public StartPanel(){

        Image background = Toolkit.getDefaultToolkit().createImage("StartScreen1.png");
        
        this.drawImage(background, 0, 0, null);
    }
    
}
