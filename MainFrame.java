import javax.swing.*;
import javax.swing.WindowConstants;

import java.awt.*;
/*
*  Evan Merzon
*  05/11/2023
*  Frames with graphics
*/

public class MainFrame extends JFrame {
    private JFrame frame;
    public CardLayout layout;
    private JPanel instructionsPanel;
    private JPanel startPanel;
    private JPanel mainPanel;
    public JPanel mainCards;

    public MainFrame() {
        frame = new JFrame("Money Moves");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        instructionsPanel = createInstructionsPanel();
        startPanel = createStartPanel();
        mainPanel = createMainPanel();
        layout = new CardLayout();
        mainCards = new JPanel(layout);
        mainCards.add(instructionsPanel, "instructions");
        mainCards.add(startPanel, "start");
        mainCards.add(mainPanel, "game");
        JPanel home = new JPanel();
        home.add(mainCards);
        layout.show(mainCards, "start");
        frame.getContentPane().add(home);
    }

    private JPanel createStartPanel() {
        JPanel res = new StartPanel(this);
        res.setPreferredSize(new Dimension(900, 700));
        res.setFocusable(true);

        return res;
    }

    private JPanel createMainPanel() {
        JPanel res = new MainGamePanel(this);
        res.setPreferredSize(new Dimension(900, 700));
        res.setBackground(new Color(50, 232, 8));
        res.setFocusable(true);

        return res;
    }

    private JPanel createInstructionsPanel() {
        JPanel res = new InstructionsPanel(this);
        res.setPreferredSize(new Dimension(900, 700));
        res.setFocusable(true);
        return res;
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }
}
