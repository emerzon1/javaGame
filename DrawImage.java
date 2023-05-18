import javax.swing.ImageIcon;
public class DrawImage {

    ImageIcon img = new ImageIcon("background.jpg");
    JPanel panel1 = new JPanel()
       @Override
       protected void paintComponent(Graphics g)
       {
          super.paintComponent(g);
          g.drawImage(img.getImage(), 0, 0, null);
       }
    
}
