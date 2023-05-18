import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
public class Temp extends JFrame{
    private JFrame frame;
    private MyPanel panel1;

    public Temp(){
        frame = new JFrame("Game Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new MyPanel();
        panel1.setPreferredSize(new Dimension(700,900));
        panel1.setBackground(new Color(123,87,240));

        panel1.setFocusable(true);
        frame.getContentPane().add(panel1);

       
    
    }
    public void display(){
        frame.pack(); // for the fram to be packed in
        frame.setVisible(true);
    }

}
