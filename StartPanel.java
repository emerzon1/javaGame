/*
*  Evan Merzon and Ryo Yoshida
*  05/25/2023
*  Start screen
*/

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

public class StartPanel extends GamePanel implements ActionListener {
    private boolean insideS = false;// true when Mouse is in start Box
    private boolean insideI = false;// True when Mouse is in Instructions Box
    private boolean clickedI = false;
    private boolean clickedS = false;
    private int numShowing = 10;
    private Timer t = new Timer(10, this);
    private ArrayList<Point> money; // the money raining from the top

    public StartPanel(MainFrame c) {
        super(c);
        money = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            for (int j = -600; j <= 0; j += 150) {
                money.add(new Point((int) (Math.random() * 900), j + ((int) (Math.random() * 200) - 100),
                        (int) (Math.random() * 360))); // create money, starting at the top but staggered with negative
                                                       // y values
            }
        }
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameUtils.drawImage("StartScreenUp1.png", g);

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

        for (Point p : money) {
            g.drawImage(GameUtils.rotateMoney((int) p.rot), p.x, p.y, null);
        }
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
            navigateTo("game");
            MainGamePanel.setPaused(false);
        }
        insideI = false;
        insideS = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        insideI = false;
        insideS = false;

        if (GameUtils.isInside(e, 350, 550, 275, 375)) {
            insideS = true; // inside start button
        }

        if (GameUtils.isInside(e, 350, 550, 400, 500)) {
            insideI = true; // inside instructions button
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Point p : money) { // move the falling money down
            p.y++;
            if (p.y >= 700) { // wrap back to top
                p.y -= 750;
                p.x = (int) (Math.random() * 900);
            }
            if (Math.random() > 0.999) { // rotation switches
                p.movingUp = !p.movingUp;
            }
            if (p.movingUp) {
                p.rot += 1.3 * Math.random(); // change the rotation
            } else {
                p.rot -= 1.3 * Math.random();
            }
        }
        repaint();
    }

    public class Point {

        public Point(int a, int b, int c) {
            x = a;
            y = b;
            rot = c;
        }

        public int x;
        public int y;
        public double rot;
        public boolean movingUp = true;
    }

}
