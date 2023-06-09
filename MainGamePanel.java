/*
*  Evan Merzon and Ryo Yoshida
*  05/25/2023
*  Main Game - shows businesses
*/

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.Timer;

public class MainGamePanel extends GamePanel implements ActionListener {
    public static boolean paused = true;
    private boolean insideHomeButton, insideClickButton;
    private boolean insideMiniGame;
    private long moneyPerClick = 1L;
    private long upgradeClickPrice = 10L;
    private Timer timer;
    private boolean smUnlocked = false; // stock market unlocked
    public long money; // how much money user has
    private boolean canWin = false;
    private boolean hasWon = false;
    private int numUnlocked = 1;
    private List<Business> businesses;

    public MainGamePanel(MainFrame c) {
        super(c); // pass in container so that navigation works
        timer = new Timer(20, this);
        money = 0L;
        timer.start();
        insideHomeButton = false;
        businesses = List.of( // initialize businesses
                new Business(100, false, 25, 200, 300, this, true, 0),
                new Business(5000, false, 25, 350, 500, this, 1),
                new Business(30000, false, 25, 500, 1000, this, 2),
                new Business(500000, false, 450, 200, 2000, this, 3),
                new Business(9000000, false, 450, 350, 3500, this, 4),
                new Business(100000000, false, 450, 500, 5000, this, 5));
    }

    public void repaintHome() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hasWon) {
            GameUtils.drawImage("StartScreenUp1.png", g); // win screen
            g.setFont(new Font("Teko", Font.BOLD, 60));
            g.drawString("CONGRATS!", 320, 300);
            g.setFont(new Font("Teko", Font.BOLD, 30));
            g.drawString("You are a masterful moneymaker", 200, 560);
            g.drawString("You are the richest person in the world!", 160, 640);
            return;
        }
        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("homeLogo.png", g, 50, 50); // show home logo in top left corner
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.BOLD, 40));
        g.drawString("Money: $" + GameUtils.format(money), 330, 40); // show user's money, formatted

        g.setColor(new Color(211, 237, 12));
        g.fillRect(200, 75, 200, 75);
        g.fillRect(450, 75, 200, 75);
        if (Minigame.level > 2) { // cannot play other levels
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(800, 100, 95, 75); // win button
        if (money >= 10_000_000_000_000L) { // user can win!
            g.fillRect(150, 600, 600, 75);
            g.setColor(new Color(0, 0, 0));
            canWin = true;
            g.drawString("Win Game!", 350, 650);
        } else { // user cannot win
            g.setColor(new Color(100, 100, 100));
            g.fillRect(150, 600, 600, 75);
            g.setColor(new Color(0, 0, 0));
            g.drawString("Need $10T to win", 270, 650);
        }
        g.setColor(new Color(100, 100, 100));
        if (smUnlocked) {
            g.setColor(new Color(211, 237, 12));
        }
        g.fillRect(800, 275, 95, 75); // stock market
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.PLAIN, 15));

        g.drawString("Upgrade Click: $" + GameUtils.format(upgradeClickPrice), 470, 125);
        g.setFont(GameUtils.buttonFont);
        g.drawString("Click for $", 220, 125);
        g.setFont(new Font("Teko", Font.PLAIN, 10));
        g.drawString("$" + GameUtils.formatNumberWithCommas(moneyPerClick) + " per click", 200, 160);
        for (Business b : businesses) { // show the businesses
            b.draw(g);
        }

        g.drawString("Play Minigame", 810, 110);
        g.drawString((3 - Minigame.level) + " Play" + (Minigame.level == 2 ? "" : "s") + " left", 810, 140);
        g.drawString(smUnlocked ? "Stock Market" : "Unlocks at $10K", 810 + (smUnlocked ? 10 : 0), 285); // stock market
    }

    public void increaseMoney(long change) {
        money += change;
        repaint(0, 0, 900, 100); // only repaint top
        if (money >= 10_000_000_000_000L) {
            repaint(0, 500, 900, 100); // optimized repaint -- only show win button
            canWin = true;
        }
        if (money >= 10000 && !smUnlocked) {
            smUnlocked = true;
            repaint(600, 100, 300, 500); // only repaint stock market button
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
        for (Business b : businesses) {
            if (b.isUnlocked() && b.wasSliding) {
                b.tick(); // move the slider
                repaint(b.getXBounds(), b.getYBounds(), (175 * b.timePassed) / b.time + 10, 75); // only repaint part of
                                                                                                 // the slider
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (GameUtils.isInside(e, 200, 400, 75, 150)) {
            insideClickButton = true;
        }
        if (GameUtils.isInside(e, 800, 900, 100, 175)) {
            insideMiniGame = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        insideHomeButton = false;
        insideClickButton = false;
        insideMiniGame = false;
        if (GameUtils.isInside(e, 0, 50, 0, 50)) {
            insideHomeButton = true;
        } else if (GameUtils.isInside(e, 200, 400, 75, 150)) {
            insideClickButton = true;
        } else if (GameUtils.isInside(e, 800, 900, 100, 175)) {
            insideMiniGame = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (insideHomeButton && GameUtils.isInside(e, 0, 50, 0, 50)) {
            navigateTo("start");
            setPaused(true);
        }
        if (insideClickButton && GameUtils.isInside(e, 200, 400, 75, 150)) {
            increaseMoney(moneyPerClick);
        }
        if (GameUtils.isInside(e, 800, 900, 100, 175) && insideMiniGame && Minigame.level <= 2) {
            navigateTo("MiniGame");
            setPaused(true);
        }
        if (GameUtils.isInside(e, 450, 650, 75, 150)) {
            if (money >= upgradeClickPrice) { // upgrading clicking
                increaseMoney(-1 * upgradeClickPrice);
                upgradeClickPrice *= 1.8;
                moneyPerClick += 1 + 0.35 * moneyPerClick;
            }
        }
        if (GameUtils.isInside(e, 150, 750, 600, 675) && canWin) {
            hasWon = true; // move to win screen
            repaint();
        }
        if (smUnlocked && GameUtils.isInside(e, 800, 895, 275, 350)) {
            navigateTo("stockMarket"); // move to stock market
            setPaused(true);
        }
        for (Business b : businesses) {
            if (b.isUnlocked()) {
                if (!b.isBought() && b.isInsideBuy(e) && money >= b.getPrice()) {
                    b.setBought(); // user is inside buy business button - buy it
                    money -= b.getPrice();
                    if (numUnlocked < businesses.size()) {
                        businesses.get(numUnlocked++).unlock(); // unlock next business
                    }
                } else if (b.isInsideBuy(e) && money >= b.getUpgradePrice()) {
                    b.upgrade(); // upgrade (if user can)
                }
                if (b.isInsideSlider(e) && b.isBought()) {
                    b.setSliding(); // start slider
                }
                if (!b.managerBought && b.isInsideManager(e)) {
                    if (money >= b.getPrice() * 13) { // buy manager
                        money -= b.getPrice() * 13;
                        b.managerBought = true;
                    }
                }
            }
        }
        repaint();
    }

}
