import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class Minigame extends GamePanel implements ActionListener, KeyListener {
    private Timer timer = new Timer(10, this);
    private int locX, locY;
    private double velocityX;
    private double velocityY;
    private double accelerationX;
    private double accelerationY;
    private boolean isJumping;
    private boolean isFalling;
    private int movingBlockVelocity1 = 4;
    private int movingBlockVelocity2 = 5;
    private int movingBlockVelocity3 = 3;

    

    private int groundY = 550;  // Y-coordinate of the ground platform
    private int mapWidth = 800; // Width of the game map
    private int mapHeight = 650; // Height of the game map

    private Rectangle platform1;
    private Rectangle platform2;
    private Rectangle platform3;
    private Rectangle platform4;
    private Rectangle endDoor;
    private Rectangle movingBlock1;
    private Rectangle movingBlock2;
    private Rectangle movingBlock3;

    public Minigame(MainFrame c) {
        super(c);
        timer.start();
        locX = 100;  // Starting X position
        locY = groundY;  // Starting Y position
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = 0.4;  // Gravity
        isJumping = false;
        isFalling = true;

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        platform1 = new Rectangle(200, 500, 200, 20);
        platform2 = new Rectangle(400, 150, 150, 20);
        platform3 = new Rectangle(100, 300, 180, 20);
        platform4 = new Rectangle(500, 350, 120, 20);
        endDoor = new Rectangle(700, 0, 50, 100);
        movingBlock1 = new Rectangle(400, 450, 50, 50);
        movingBlock2 = new Rectangle(400, 100, 50, 50);
        movingBlock3 = new Rectangle(400, 300, 50, 50);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        g.setColor(Color.BLUE);
        g.fillRect((int) locX, (int) locY, 50, 50);  // Player character

        g.setColor(Color.GREEN);
        g.fillRect(0, groundY + 50, getWidth(), 50);  // Ground platform

        g.setColor(Color.GRAY);
        g.fillRect(platform1.x, platform1.y, platform1.width, platform1.height); // Platform 1
        g.fillRect(platform2.x, platform2.y, platform2.width, platform2.height); // Platform 2
        g.fillRect(platform3.x, platform3.y, platform3.width, platform3.height); // Platform 3
        g.fillRect(platform4.x, platform4.y, platform4.width, platform4.height); // Platform 4

        g.setColor(Color.ORANGE);
        g.fillRect(endDoor.x, endDoor.y, endDoor.width, endDoor.height); // End door

        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("homeLogo.png", g, 50, 50);
        g.setColor(Color.RED);
        g.fillRect(movingBlock1.x, movingBlock1.y, movingBlock1.width, movingBlock1.height);
        g.fillRect(movingBlock2.x, movingBlock2.y, movingBlock2.width, movingBlock2.height);
        g.fillRect(movingBlock3.x, movingBlock3.y, movingBlock3.width, movingBlock3.height);

        g.drawString("Use the right and left arrow keys to move.\n Space to Jump. \n Avoid the moving red square. \n Reach the yellow door on the top to gain money!!",0,250);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        velocityX += accelerationX;
        velocityY += accelerationY;

        if (velocityX > 5) {
            velocityX = 5;
        } else if (velocityX < -5) {
            velocityX = -5;
        }

        locX += velocityX;

        // Restrict movement within the map boundaries
        if (locX < 0) {
            locX = 0;
        } else if (locX > mapWidth +50) {  // Assuming player width is 50
            locX = mapWidth+50;
        }

        locY += velocityY;

        // Check collision with platforms
        if (locY + 50 >= platform1.y && locY + 50 <= platform1.y + platform1.height && velocityY > 0 && locX + 50 >= platform1.x && locX <= platform1.x + platform1.width) {
            velocityY = 0;
            isFalling = false;
            locY = platform1.y - 50;
        } else if (locY + 50 >= platform2.y && locY + 50 <= platform2.y + platform2.height && velocityY > 0 && locX + 50 >= platform2.x && locX <= platform2.x + platform2.width) {
            velocityY = 0;
            isFalling = false;
            locY = platform2.y - 50;
        } else if (locY + 50 >= platform3.y && locY + 50 <= platform3.y + platform3.height && velocityY > 0 && locX + 50 >= platform3.x && locX <= platform3.x + platform3.width) {
            velocityY = 0;
            isFalling = false;
            locY = platform3.y - 50;
        } else if (locY + 50 >= platform4.y && locY + 50 <= platform4.y + platform4.height && velocityY > 0 && locX + 50 >= platform4.x && locX <= platform4.x + platform4.width) {
            velocityY = 0;
            isFalling = false;
            locY = platform4.y - 50;
        } else {
            isFalling = true;
        }

        // Check if the character falls off the platform
        if (locY > groundY && isFalling) {
            velocityY = 0;
            locY = groundY;
        }

        // Check if the character reaches the end door
        if (locY + 50 >= endDoor.y && locY <= endDoor.y + endDoor.height && locX + 50 >= endDoor.x && locX <= endDoor.x + endDoor.width) {
            navigateTo("game");
        }
        movingBlock1.x += movingBlockVelocity1;
        movingBlock2.x += movingBlockVelocity2;
        movingBlock3.x += movingBlockVelocity3;
        

        if (locX + 50 >= movingBlock1.x && locX <= movingBlock1.x + movingBlock1.width && locY + 50 >= movingBlock1.y && locY <= movingBlock1.y + movingBlock1.height) {
            locX = 100;
            locY=groundY;
        }
        if (locX + 50 >= movingBlock2.x && locX <= movingBlock2.x + movingBlock2.width && locY + 50 >= movingBlock2.y && locY <= movingBlock2.y + movingBlock2.height) {
            locX = 100;
            locY=groundY;
        }
        if (locX + 50 >= movingBlock3.x && locX <= movingBlock3.x + movingBlock3.width && locY + 50 >= movingBlock3.y && locY <= movingBlock3.y + movingBlock3.height) {
            locX = 100;
            locY=groundY;
        }

        // Reverse the block's direction if it reaches the map boundaries
        if (movingBlock1.x <= 0 || movingBlock1.x + movingBlock1.width >= mapWidth) {
            movingBlockVelocity1 *= -1;
        }
        if (movingBlock2.x <= 0 || movingBlock2.x + movingBlock2.width >= mapWidth) {
            movingBlockVelocity2 *= -1;
        }
        if (movingBlock3.x <= 0 || movingBlock3.x + movingBlock3.width >= mapWidth) {
            movingBlockVelocity3 *= -1;
        }
    
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE && (locY == groundY || !isFalling)) {
            velocityY = -10;
            isJumping = true;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            accelerationX = -0.3;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            accelerationX = 0.3;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && accelerationX < 0) {
            accelerationX = 0;
        }
        if (keyCode == KeyEvent.VK_RIGHT && accelerationX > 0) {
            accelerationX = 0;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            isJumping = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (GameUtils.isInside(e, 0, 50, 0, 50)) {
            navigateTo("game");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (GameUtils.isInside(e, 0, 50, 0, 50)) {
            navigateTo("game");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}