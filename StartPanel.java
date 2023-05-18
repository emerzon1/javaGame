
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartPanel extends JPanel implements MouseListener{

    public StartPanel() {
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("StartScreen1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, null);
        g.setColor(new Color(211, 237, 12));
        g.fillRect(350,275,200,100);


        

    }

    public void mouseClicked(MouseEvent e){

  }
  public void	mouseEntered(MouseEvent e){
    int X = e.getX();
    int Y = e.getY();
  }
  public void mousePressed(MouseEvent e){
    int X = e.getX();
    int Y = e.getY();
    System.out.println("X: " + X + "Y: " + Y);
    if((X>=350 && X<=550)&& (Y>=275 && Y<=375)){
        System.out.println("IN BOUNDS");
    }
  }
  public void mouseReleased(MouseEvent e){
    
  }
@Override
public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
}


}
