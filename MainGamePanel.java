import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class MainGamePanel extends GamePanel implements ActionListener {
    public static boolean paused = true;
    private boolean insideHomeButton,insideClickButton,insideBus1,insideBus2,insideBus3,insideBus4,insideBus5;
    private long moneyPerClick = 1L;
    private long upgradeClickPrice = 10L;
    private Timer timer;
    private long money;
    private int xSlide;
    private List<Business> businesses;

    public long getMoney(){
        return money;
    }

    public MainGamePanel(MainFrame c) {
        super(c);
        timer = new Timer(10, this);
        money = 0L;
        timer.start();
        insideHomeButton = false;
        businesses = List.of(
            new Business("Lemonade", 1000, false , 25, 200),
            new Business("Bus2",10000, false, 25, 300 ),
            new Business("Bus3",100000, false, 25, 400 ),
            new Business("Bus4",1000000, false, 25, 500 ),
            new Business("Bus5",10000000, false, 25, 600 )
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("homeLogo.png", g, 50, 50);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.BOLD, 40));
        g.drawString("Money: $" + GameUtils.format(money), 330, 40);

        g.setColor(new Color(211, 237, 12));
        g.fillRect(200, 75, 200, 75);
        g.fillRect(450, 75, 200, 75);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.PLAIN, 15));

        g.drawString("Upgrade Click: $" + GameUtils.format(upgradeClickPrice), 470, 125);
        g.setFont(GameUtils.buttonFont);
        g.drawString("Click for $", 220, 125);
        g.setFont(new Font("Teko", Font.PLAIN, 10));
        g.drawString("$" + GameUtils.formatNumberWithCommas(moneyPerClick) + " per click", 200, 160);
        for (Business b : businesses) {
            b.Draw(g);
        }
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
        if (GameUtils.isInside(e, 450, 650, 75, 150)) {
            if (money >= upgradeClickPrice) {
                money -= upgradeClickPrice;
                upgradeClickPrice *= 1.8;
                moneyPerClick += 1 + 0.35 * moneyPerClick;
            }
        }
        repaint();
    }

}
