import javax.swing.*;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.*;
/*
*  Evan Merzon
*  05/11/2023
*  Frames with graphics
*/

public class MainFrame extends JFrame {
    private JFrame frame;
    private JPanel instructionsPanel;
    private JPanel startPanel;

    public MainFrame() {
        frame = new JFrame("Money Moves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        instructionsPanel = createInstructionsPanel();
        startPanel = createStartPanel();

        frame.getContentPane().add(startPanel);
    }

    private JPanel createStartPanel() {
        JPanel res = new StartPanel();
        res.setPreferredSize(new Dimension(900, 700));
        res.setFocusable(true);

        return res;
    }

    private JPanel createInstructionsPanel() {
        JPanel res = new InstructionsPanel();
        GridLayout l = new GridLayout(9, 3);
        res.setLayout(l);
        res.setPreferredSize(new Dimension(500, 500));
        res.setBackground(new Color(20, 120, 138));
        res.setFocusable(true);
        JButton startButton = new JButton("Return Home");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("fixed");
            }
        });
        for (int i = 0; i < 32; i++) {
            res.add(new JPanel());
        }
        res.add(startButton, JLabel.CENTER);
        // l.setComponentAlignment(startButton, Alignment.CENTER);
        return res;
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }
}
