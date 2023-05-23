import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            background = ImageIO.read(new File(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, null);
    }

    public static final Font buttonFont = new Font("Teko", Font.PLAIN, 30);
    public static final String INSTRUCTIONS = "Welcome to Money Moves. Your goal is to grow your business and make as much money as possible";
}
