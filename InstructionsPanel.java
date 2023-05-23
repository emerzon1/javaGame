import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InstructionsPanel extends GamePanel {
    private boolean hasClicked = false;
    private boolean insideHome = false;

    public InstructionsPanel(MainFrame c) {
        super(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameUtils.drawImage("StartScreen1.png", g);
        g.setFont(new Font("Teko", Font.PLAIN, 20));
        g.setColor(new Color(50, 232, 8));
        g.fillRect(172, 180, 553, 300);
        g.setColor(new Color(0, 0, 0));
        int stringWidth = 50;
        for (int i = 0; i <= GameUtils.INSTRUCTIONS.length(); i += stringWidth) {
            g.drawString(
                    i + stringWidth > GameUtils.INSTRUCTIONS.length() ? GameUtils.INSTRUCTIONS.substring(i)
                            : GameUtils.INSTRUCTIONS.substring(i, i + stringWidth),
                    180, 200 + (i / stringWidth) * 30);
        }

        g.setColor(new Color(211, 237, 12));
        if (insideHome) {
            g.setColor(new Color(3, 205, 255));
        }
        g.setFont(GameUtils.buttonFont);
        g.fillRect(325, 500, 200, 100);
        g.setColor(new Color(0, 0, 0));
        g.drawString("Go Home", 355, 563);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (GameUtils.isInside(e, 325, 525, 500, 600)) {
            hasClicked = true;
            insideHome = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (GameUtils.isInside(e, 325, 525, 500, 600) && hasClicked) {
            navigateTo("start");
        }
        hasClicked = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (GameUtils.isInside(e, 325, 525, 500, 600)) {
            insideHome = true;
        } else {
            insideHome = false;
        }
        repaint();

    }
}
