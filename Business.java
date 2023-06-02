import java.awt.*;
import java.awt.event.*;

public class Business {
    private long price;
    private long upgrade;
    private long money;
    private boolean bought;
    private boolean unlocked;
    private String name;
    private int xPos, yPos;
    public boolean isSliding;
    public boolean wasSliding;
    private int level = 1;
    public boolean managerBought = false;
    private int time;
    private int timePassed;
    private MainGamePanel container;

    public void tick() {
        if (isSliding) {
            timePassed += 10;
        }
    }

    public long getPrice() {
        return price;
    }

    public long getUpgradePrice() {
        return upgrade;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought() {
        bought = true;
    }

    private void setUnlocked() {
        unlocked = true;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public Business(String n, long p, boolean b, int x, int y, int time, MainGamePanel g) {
        super();
        name = n;
        xPos = x;
        yPos = y;
        price = p;
        bought = b;
        container = g;
        this.time = time;
        isSliding = false;
        defaultStuff();
    }

    public Business(String n, int p, boolean b, int x, int y, int time, MainGamePanel g, boolean unlocked) {
        super();
        name = n;
        xPos = x;
        yPos = y;
        price = p;
        bought = b;
        container = g;
        this.time = time;
        isSliding = false;
        this.unlocked = unlocked;
        defaultStuff();
    }

    public void defaultStuff() {
        upgrade = (long) (price * 1.2);
        money = price / 10;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(211, 237, 12));
        // Business Buttons
        if (!bought) {
            g.setColor(new Color(150, 150, 150, 150));
        }
        g.fillRect(xPos, yPos, 175, 75);// First Business

        g.setColor(new Color(255, 140, 0));
        // Buy / Upgrade Buttons
        if (!unlocked || level == 100) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(xPos + 180, yPos, 150, 75);
        if (bought && !managerBought) {
            g.fillRect(xPos, yPos - 30, 100, 25);
            g.setColor(new Color(0, 0, 0));
            g.drawString("Manager: $" + GameUtils.format(price * 13), xPos + 10, yPos - 15);
        }
        g.setColor(new Color(0, 0, 0));
        g.setFont(GameUtils.buttonFont);
        g.drawString(bought ? "Upgrade" : "Buy", xPos + (bought ? 192 : 220), yPos + 50);
        g.setFont(new Font("Teko", Font.PLAIN, 10));
        g.drawString("Price: " + GameUtils.format(bought ? upgrade : price), xPos + 185, yPos + 90);
        if (bought) {
            g.drawString("$" + GameUtils.format(money) + " / fill", xPos + 10, yPos + 90);
        }
        if (isSliding) {
            g.setColor(new Color(255, 0, 0));
            g.fillRect(xPos, yPos, (175 * timePassed) / time, 75);
            g.setColor(new Color(0, 0, 0));
        } else {
            wasSliding = false;
        }
        // draw icons
        GameUtils.drawImage("Lem_Icon.png", g, 150, 75, 25, 200);
        GameUtils.drawImage("Fish_Icon.png", g, 150, 75, 25, 350);
        GameUtils.drawImage("Piz_Icon.png", g, 150, 75, 25, 500);
        GameUtils.drawImage("Film_Icon.png", g, 150, 75, 450, 200);
        GameUtils.drawImage("Oil_Icon.png", g, 150, 75, 450, 350);

        g.setColor(new Color(0, 0, 0));
        if (timePassed >= time) {
            timePassed -= time;
            container.increaseMoney(money);
            if (!managerBought) {
                isSliding = false;
            }
        }
        if (bought) {
            GameUtils.drawImage("click.png", g, 16, 15, xPos + 125, yPos + 5);
            GameUtils.drawImage("money.png", g, 30, 30, xPos + 145, yPos);
            g.setColor(new Color(0, 0, 0));
            g.drawString("Level " + level, xPos + 185, yPos + 10);
        }
        if (managerBought) {

            isSliding = true;
            wasSliding = true;
        }
    }

    public void unlock() {
        this.unlocked = true;
    }

    public int getXBounds() {
        return xPos;
    }

    public int getYBounds() {
        return yPos;
    }

    public void upgrade() {
        if (level == 100) {
            return;
        }
        container.increaseMoney(-1 * upgrade);
        money *= 1.2;
        upgrade *= 1.2;
        level++;
        if (level % 20 == 0) {
            time = (int) Math.round(time * 0.80 / 10) * 10;
        }
    }

    public boolean isInsideBuy(MouseEvent e) {
        return GameUtils.isInside(e, xPos + 175, xPos + 325, yPos, yPos + 75);
    }

    public boolean isInsideManager(MouseEvent e) {
        return GameUtils.isInside(e, xPos, xPos + 100, yPos - 30, yPos - 5);
    }

    public boolean isInsideSlider(MouseEvent e) {
        return GameUtils.isInside(e, xPos, xPos + 175, yPos, yPos + 75);
    }

    public void setSliding() {
        isSliding = true;
        wasSliding = true;
    }

}
