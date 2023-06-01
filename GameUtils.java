import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class GameUtils {
    private GameUtils() {

    }

    public static boolean isInside(MouseEvent e, int startX, int endX, int startY, int endY) {
        return e.getX() >= startX && e.getX() <= endX && e.getY() >= startY && e.getY() <= endY;
    }

    public static void drawImage(String image, Graphics g) {
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("./images/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            background = ImageIO.read(new File("./images/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
    }

    public static void drawImage(String image, int x, int y, Graphics g) {
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("./images/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background, x, y, null);
    }

    public static void drawImage(String image, Graphics g, int width, int height, int x, int y) {
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("./images/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background.getScaledInstance(width, height, Image.SCALE_SMOOTH), x, y, null);
    }

    public static final Font buttonFont = new Font("Teko", Font.PLAIN, 30);
    public static final String INSTRUCTIONS = "Welcome to Money Moves. Your goal is to grow your business and make as much money as possible";
}
