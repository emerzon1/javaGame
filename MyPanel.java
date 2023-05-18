import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;


public class MyPanel extends JPanel implements ActionListener{
    private Timer time = new Timer(5, this);

    private int locX;
    private int locY;

    public MyPanel(){
        time.start();
        //location of randomly moving object
        locX= 500;
        locY =200;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);


    }
    public void actionPerformed(ActionEvent e){
       
    }
    }

