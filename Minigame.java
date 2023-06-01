import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Minigame extends GamePanel implements ActionListener, KeyListener {
    private Timer timer;
    private int locX, locY;
    private int velocityY;
    private boolean isJumping;
    private boolean isFalling;

    public Minigame(MainFrame c) {
        super(c);
        timer = new Timer(5, this);
        timer.start();

        locX = 100;  // Starting X position
        locY = 200;  // Starting Y position

        velocityY = 0;
        isJumping = false;
        isFalling = true;

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        g.setColor(Color.BLUE);
        g.fillRect(locX, locY, 50, 50);  // Player character

        g.setColor(Color.GREEN);
        g.fillRect(0, 600, getWidth(), 50);  // Ground platform
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isJumping) {
            velocityY -= 1;  // Apply jump velocity

            if (velocityY <= 0) {
                isJumping = false;
                isFalling = true;
            }
        } else if (isFalling) {
            velocityY += 1;  // Apply gravity

            if (locY + velocityY >= 200) {
                velocityY = 0;
                isFalling = false;
            }
        }

        locY += velocityY;

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_SPACE && !isJumping && !isFalling) {
            isJumping = true;
            velocityY = 15;  // Initial jump velocity
        } else if (keyCode == KeyEvent.VK_LEFT) {
            locX -= 5;  // Move left by 5 pixels
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            locX += 5;  // Move right by 5 pixels
        }
        
        repaint();  // Repaint the component to update the position
    }

    // Other unused KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
