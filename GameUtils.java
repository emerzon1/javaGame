import java.awt.Font;
import java.awt.event.MouseEvent;

public class GameUtils {
    public static boolean isInside(MouseEvent e, int startX, int endX, int startY, int endY) {
        return e.getX() >= startX && e.getX() <= endX && e.getY() >= startY && e.getY() <= endY;
    }

    public static Font buttonFont = new Font("Teko", Font.PLAIN, 30);
    public static String instructions = "Welcome to Money Moves. Your goal is to grow your business and make as much money as possible";
}
