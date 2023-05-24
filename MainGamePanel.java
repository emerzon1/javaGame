import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;

public class MainGamePanel extends GamePanel implements ActionListener {
    public static boolean paused = true;
    private boolean insideHomeButton;
    private boolean insideClickButton;
    private long moneyPerClick = 1L;
    private Timer timer;
    private long money;

    public MainGamePanel(MainFrame c) {
        super(c);
        timer = new Timer(10, this);
        money = 0;
        timer.start();
        insideHomeButton = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("homeLogo.png", g, 50, 50);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.BOLD, 40));
        g.drawString("Money: $" + money, 330, 40);

        g.setColor(new Color(211, 237, 12));
        g.fillRect(200, 75, 200, 75);
        g.setColor(new Color(0, 0, 0));
        g.setFont(GameUtils.buttonFont);
        g.drawString("Click for $", 220, 125);
    }

    public static void setPaused(boolean b) {
        paused = b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (paused) {
            return;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (GameUtils.isInside(e, 200, 400, 75, 150)) {
            insideClickButton = true;
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        insideHomeButton = false;
        insideClickButton = false;
        if (GameUtils.isInside(e, 0, 50, 0, 50)) {
            insideHomeButton = true;
        } else if (GameUtils.isInside(e, 200, 400, 75, 150)) {
            insideClickButton = true;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (insideHomeButton && GameUtils.isInside(e, 0, 50, 0, 50)) {
            navigateTo("start");
            setPaused(true);
        }
        if (insideClickButton && GameUtils.isInside(e, 200, 400, 75, 150)) {
            money += moneyPerClick;
        }
        repaint();
    }

}
