import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameUtils {
    private static Map<String, BufferedImage> images = new HashMap<>();

    private GameUtils() {
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("./images/moneyMini.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        images.put("moneyMini", background);
    }

    public static boolean isInside(MouseEvent e, int startX, int endX, int startY, int endY) {
        return e.getX() >= startX && e.getX() <= endX && e.getY() >= startY && e.getY() <= endY;
    }

    public static void drawImage(String image, Graphics g) {
        BufferedImage background = null;
        if (!images.containsKey(image)) {
            try {
                background = ImageIO.read(new File("./images/" + image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(image, background);
        }
        background = images.get(image);
        g.drawImage(background, 0, 0, null);
    }

    public static String formatNumberWithCommas(long l) {
        return String.format("%,d", l);
    }

    private static final TreeMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "K");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "q");
        suffixes.put(1_000_000_000_000_000_000L, "Q");
    }

    public static String format(long value) {
        // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE)
            return format(Long.MIN_VALUE + 1);
        if (value < 0)
            return "-" + format(-value);
        if (value < 1000)
            return Long.toString(value); // deal with easy case

        Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); // the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static void drawImage(String image, Graphics g, int width, int height) {
        BufferedImage background = null;
        if (!images.containsKey(image)) {
            try {
                background = ImageIO.read(new File("./images/" + image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(image, background);
        }
        background = images.get(image);
        g.drawImage(background.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
    }

    public static void drawImage(String image, int x, int y, Graphics g) {
        BufferedImage background = null;
        if (!images.containsKey(image)) {
            try {
                background = ImageIO.read(new File("./images/" + image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(image, background);
        }
        background = images.get(image);
        g.drawImage(background, x, y, null);
    }

    public static BufferedImage rotateMoney(int degree) {
        BufferedImage background = null;
        if (!images.containsKey("moneyMini")) {
            try {
                background = ImageIO.read(new File("./images/moneyMini.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put("moneyMini", background);
        }
        double deg = Math.toRadians(degree);
        BufferedImage bufImg = images.get("moneyMini");
        double sin = Math.abs(Math.sin(deg)), cos = Math.abs(Math.cos(deg));
        int w = bufImg.getWidth(), h = bufImg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(deg, w / 2, h / 2);
        g.drawRenderedImage(bufImg, null);
        g.dispose();
        return result;
    }

    public static void drawImage(String image, Graphics g, int width, int height, int x, int y) {
        BufferedImage background = null;
        if (!images.containsKey(image)) {
            System.out.println("not cached");
            try {
                background = ImageIO.read(new File("./images/" + image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(image, background);
        }
        background = images.get(image);
        g.drawImage(background.getScaledInstance(width, height, Image.SCALE_SMOOTH), x, y, null);
    }

    public static void drawImage(String image, Graphics g, int width, int height, int x, int y, JPanel observer) {
        BufferedImage background = null;
        if (!images.containsKey(image)) {
            try {
                background = ImageIO.read(new File("./images/" + image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(image, background);
        }
        background = images.get(image);
        g.drawImage(background.getScaledInstance(width, height, Image.SCALE_SMOOTH), x, y, observer);
    }

    public static final Font buttonFont = new Font("Teko", Font.PLAIN, 30);
    public static final String INSTRUCTIONS = "Welcome to Money Moves. Your goal is to grow your business and make asmuch money as possible.  You start with $0 and win when you get to onetrillion dollars.  "
            +
            "At the beginning, you can gain money by clicking a button, but you won't make much from that.  You should upgrade this inthe beginning to get enough money to buy your first business.  "
            +
            "As you get more and more money, you can invest in better businesses.  Click  the bar to the side to start working, "
            +
            "and when the red progress bar   fills up, you will get paid.  When you get enough money, you can hire a manager so that you don't have to manually click each time you want to start working."
            +
            "  Upgrade the businesses to make more money each     payout day. Every 10 levels, the business will get a big boost.  Twiceduring your journey, you are allowed to play the minigame, which will 10x your money on successful completion.  "
            +
            "There  is also an in-game   market, which unlocks when you reach $10,000. You can invest your     money in commodities (oil, gold, or diamond).  Oil is the cheapest andleast volatile.  "
            +
            "Gold is generally more expensive and riskier, and    diamond is the riskiest and most expensive.  Good luck!";
}
