import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Business implements ActionListener, MouseListener{
    private boolean isInside;
    private int price;
    private boolean bought;
    private String name;
    private int xPos,yPos;
    private boolean isSliding;
    private int xSlide;
    private Timer time = new Timer();

    

    public int getPrice(){
        return price;
    }
    public boolean isBought(){
        return bought;
    }
    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }

    public Business(String n, int p, boolean b, int x, int y){
        
        name = n;
        xPos= x;
        yPos = y;
        price = p;
        bought = b;
        isInside = false;
        isSliding = false;
    }

    
    public void Draw(Graphics g) {
        g.setColor(new Color(211, 237, 12));
        //Business Buttons
        g.fillRect(xPos, yPos, 150, 75);//First Business

        g.setColor(new Color(255,140,0));
        //Buy / Upgrade Buttons
        g.fillRect(200,yPos,150,75 );
        g.setColor(new Color(0,0,0));
        g.setFont(GameUtils.buttonFont);
        g.drawString("Buy",245,yPos+50 );
        if (isInside && isSliding) {
            System.out.println("Working");
            g.setColor(new Color(255, 0, 0));
            g.fillRect(xPos, yPos, xSlide, 25);
        }
        //draw icons
        GameUtils.drawImage("Lem_Icon.png",g, 25, 200, 150,75);
        GameUtils.drawImage("Fish_Icon.png",g, 25, 300, 150,75);
        GameUtils.drawImage("Piz_Icon.png",g, 25, 400, 150,75);
        GameUtils.drawImage("Film_Icon.png",g, 25, 500, 150,75);
        GameUtils.drawImage("Oil_Icon.png",g, 25, 600, 150,75);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (GameUtils.isInside(e, xPos, xPos + 150, yPos, yPos + 75)) {
            isInside = true;
            isSliding = true;
            System.out.println("Pressed");
            
        } else {
            isInside = false;
        }
        if(isSliding&& xSlide <=150)
        xSlide+=1;
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }


    
}
