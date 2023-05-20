
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StartPanel extends GamePanel {
    private boolean insideS = false;// true when Mouse is in start Box
    private boolean insideI = false;// True when Mouse is in Instructions Box
    private boolean clickedI = false;
    private boolean clickedS = false;

    public StartPanel(MainFrame c) {
        super(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("StartScreenUp1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, null);

        g.setColor(new Color(211, 237, 12));
        g.fillRect(350, 275, 200, 100);// Start Box
        g.fillRect(350, 400, 200, 100);// Instructions Box

        g.setColor(new Color(3, 205, 255));
        if (insideS) {
            g.fillRect(350, 275, 200, 100);
        }
        if (insideI) {
            g.fillRect(350, 400, 200, 100);
        }

        // draw text
        g.setColor(new Color(0, 0, 0));
        g.setFont(GameUtils.buttonFont);
        g.drawString("Start", 410, 330);// Start Text
        g.drawString("Instructions", 360, 465);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (insideI) {
            clickedI = true;
        }
        if (insideS) {
            clickedS = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (insideI && clickedI) {
            navigateTo("instructions");
        }
        if (insideS && clickedS) {
            System.out.println("START");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        insideI = false;
        insideS = false;

        if (GameUtils.isInside(e, 350, 550, 275, 375)) {
            insideS = true;
        }

        if (GameUtils.isInside(e, 350, 550, 400, 500)) {
            insideI = true;
        }
        repaint();
    }

}
