
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.naming.spi.NamingManager;
import javax.swing.*;

public class StartPanel extends JPanel implements MouseListener, MouseMotionListener {
    private boolean insideS = false;//true when Mouse is in start Box
    private boolean insideI = false;//True when Mouse is in Instructions Box
    private JLabel label;
    private JLabel statusbar;//StatusBar on the Top of the Page

    public StartPanel() {
        statusbar = new JLabel("default");
        add(statusbar,BorderLayout.SOUTH);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("StartScreenUp1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.setColor(new Color(211, 237, 12));
        g.drawImage(background, 0, 0, null);
        g.fillRect(350,275,200,100);//Start Box
        g.fillRect(350, 400,200,100);//Instructions Box
        g.setColor(new Color(0, 0, 12));
        g.setFont(new Font("Teko", Font.ITALIC, 35  ));
        g.drawString("Start",410 ,330);//Start Text
        g.drawString("Instructions",350 ,465);
        g.setColor(new Color(12, 12, 0));
        if(insideS) {
            g.setColor(new Color(3, 205, 255));
            g.fillRect(350, 275, 200, 100);
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("Teko", Font.ITALIC, 35  ));
            g.drawString("Start",410 ,330);

        }
        if(insideI){
            g.setColor(new Color(3, 205, 255));
            g.fillRect(350, 400, 200, 100);
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("Teko", Font.ITALIC, 35  ));
            g.drawString("Instructions",350 ,465);
        }
    }

    public void mouseClicked(MouseEvent e){

  }
  public void mouseEntered(MouseEvent e){
    
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
    int X = e.getX();
    int Y = e.getY();
    if(!(X>=350 && X<=550)&& !(Y>=275 && Y<=375))
        statusbar.setText("Outside the Window");
}

@Override
public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

}

@Override
public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub
    int X = e.getX();
    int Y = e.getY();

    if((X>=350 && X<=550)&& (Y>=275 && Y<=375)){
        statusbar.setText("In the box");
        //paintImmediately(325, 250, 400,200);
        insideS = true;
        repaint();
    } else{
        statusbar.setText("Outside the box");
       // paintImmediately((350,275,200,100));
        insideS = false;
        repaint();
    }
    if((X>=350 && X<= 550) && (Y>=400 && Y<=500)){
        statusbar.setText("In the box");
        //paintImmediately(325, 250, 400,200);
        insideI = true;
        repaint();
    } else{
        statusbar.setText("Outside the box");
       // paintImmediately((350,275,200,100));
        insideI = false;
        repaint();
    }


}


}
