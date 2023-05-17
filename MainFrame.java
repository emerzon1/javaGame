import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;
/*
*  Evan Merzon
*  05/11/2023
*  Frames with graphics
*/

public class MainFrame extends JFrame {
	private JFrame frame;
	private MyPanel panel1;
	
	public MainFrame() {
		frame = new JFrame("My first frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new MyPanel();
		panel1.setPreferredSize(new Dimension(700, 900));
		panel1.setBackground(new Color(20, 120, 138));
		panel1.setFocusable(true);

		frame.getContentPane().add(panel1);
	}
	
	public void display() {
		frame.pack();
		frame.setVisible(true);
	}
}

}
