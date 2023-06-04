import java.awt.*;
import java.awt.event.*;

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
        g.setFont(new Font("Teko", Font.PLAIN, 15));
        g.setColor(new Color(50, 232, 8));
        g.fillRect(172, 180, 553, 700);
        g.setColor(new Color(0, 0, 0));
        int stringWidth = 70;
        for (int i = 0; i <= GameUtils.INSTRUCTIONS.length(); i += stringWidth) {
            g.drawString(
                    i + stringWidth > GameUtils.INSTRUCTIONS.length() ? GameUtils.INSTRUCTIONS.substring(i)
                            : GameUtils.INSTRUCTIONS.substring(i, i + stringWidth),
                    180, 180 + (i / stringWidth) * 25);
        }

        g.setColor(new Color(211, 237, 12));
        if (insideHome) {
            g.setColor(new Color(3, 205, 255));
        }
        g.setFont(GameUtils.buttonFont);
        g.fillRect(325, 590, 200, 100);
        g.setColor(new Color(0, 0, 0));
        g.drawString("Go Home", 355, 653);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (GameUtils.isInside(e, 325, 525, 590, 690)) {
            hasClicked = true;
            insideHome = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (GameUtils.isInside(e, 325, 525, 590, 690) && hasClicked) {
            navigateTo("start");
        }
        hasClicked = false;
        insideHome = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (GameUtils.isInside(e, 325, 525, 590, 690)) {
            insideHome = true;
        } else {
            insideHome = false;
        }
        repaint();

    }
}
