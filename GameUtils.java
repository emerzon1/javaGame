import java.awt.Font;
import java.awt.event.MouseEvent;

public class GameUtils {
    private GameUtils() {

    }

    public static boolean isInside(MouseEvent e, int startX, int endX, int startY, int endY) {
        return e.getX() >= startX && e.getX() <= endX && e.getY() >= startY && e.getY() <= endY;
    }

    public static final Font buttonFont = new Font("Teko", Font.PLAIN, 30);
    public static final String INSTRUCTIONS = "Welcome to Money Moves. Your goal is to grow your business and make as much money as possible";
}
