
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
    private ArrayList<Point> money;

    public StartPanel(MainFrame c) {
        super(c);
        money = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            for (int j = -600; j <= 0; j += 150) {
                money.add(new Point((int) (Math.random() * 900), j + ((int) (Math.random() * 200) - 100),
                        (int) (Math.random() * 360)));
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
            insideS = true;
        }

        if (GameUtils.isInside(e, 350, 550, 400, 500)) {
            insideI = true;
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        for (Point p : money) {
            p.y++;
            if (p.y >= 600) {
                p.y -= 600;
            }
            if (p.rot >= 50) {
                p.movingUp = false;
            } else if (p.rot <= -50) {
                p.movingUp = true;
            }
            if (p.movingUp) {
                p.rot += .75;
            } else {
                p.rot -= .75;
            }
            // if (p.rot < 0) {
            // p.rot += 360;
            // }
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
