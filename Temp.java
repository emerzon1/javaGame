import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;


public class Temp extends JPanel implements ActionListener, KeyListener{
    private Timer time = new Timer(5, this);

    private int locX;
    private int locY;
    private int myX;
    private int myY;
    private int changeX;
    private int changeY;
    private double changeMyX;
    private double changeMyY;

    public Temp(){
        time.start();
        //location of randomly moving object
        locX= 500;
        locY =200;
        //amount of change for randomly moving object
        changeX = 1;
        changeY=1;
        // location of object controllable by keyboard
        myX = 350;
        myY = 550;
        //amount of change for controllable object
        changeMyX = 0;
        changeMyY = 0;
        //adding keyListener to the component

        addKeyListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(locX,locY,300,200);
        g.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
        g.fillOval(30,200,45,45);
        g.setColor(new Color(200,150,80));
        g.fillRect(myX,myY,50,50);
        g.setColor(new Color(200,30,0));

    }
    public void actionPerformed(ActionEvent e){
        changeX = 1;
        changeY= 1;
        locX+=changeX;
        locY+=changeY;
        if(locX>700){
            locX = 0;
        }
        if(locY>700){
            locY = 0;
        }
        if(locX<0){
            changeX=1;
        }
        if(changeY <0){
            changeY=1;
        }
        if(myX <0){
            changeMyX = 0;
        }
        if(myX>700){
            changeMyX = 0;
        }
        if(myY<0){
            changeMyY= 0;
        }
        if(myY>900){
            changeY= 0;
        }
        locX+=changeX;
        locY += changeY;
        myX += changeMyX;
        myY+=changeMyY;
        changeMyX *=0.9;
        changeMyY *= 0.9;
        repaint();
    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
    public void keyPressed(KeyEvent e){
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
            changeMyX = -1;
        }
        if(c== KeyEvent.VK_RIGHT){
            changeMyX = 1;

        }
        if(c==KeyEvent.VK_UP){
            changeMyY  = -1;
        }
        if(c==KeyEvent.VK_DOWN){
            changeMyY = 1;
        }
    }

    }

