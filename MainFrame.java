/*
*  Evan Merzon and Ryo Yoshida
*  05/25/2023
*  Home frame
*/

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.WindowConstants;

import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private JFrame frame;
    public CardLayout layout;
    private JPanel instructionsPanel;
    private JPanel startPanel;
    public JPanel mainPanel;
    private JPanel miniGame;
    private JPanel stockMarket;
    public JPanel mainCards;

    private static Clip music;

    public MainFrame() {
        frame = new JFrame("Money Moves");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // create panels
        instructionsPanel = createInstructionsPanel();
        startPanel = createStartPanel();
        mainPanel = createMainPanel();
        layout = new CardLayout();
        miniGame = createMiniGamePanel();
        stockMarket = createStockMarketPanel();

        // add panels to cardLayout
        mainCards = new JPanel(layout);
        mainCards.add(instructionsPanel, "instructions");
        mainCards.add(startPanel, "start");
        mainCards.add(mainPanel, "game");
        mainCards.add(miniGame, "MiniGame");
        mainCards.add(stockMarket, "stockMarket");

        // display cardLayout in different JPanel (idk why we needed to do this, but it
        // wasn't working w/o this)
        JPanel home = new JPanel();
        home.add(mainCards);
        layout.show(mainCards, "start");
        frame.getContentPane().add(home);

        // play music
        try {
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("./sounds/Music.wav"));
            // Get a sound clip resource.
            music = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            music.open(audioIn);
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private JPanel createMiniGamePanel() {
        JPanel res = new Minigame(this);
        res.setPreferredSize(new Dimension(900, 700));
        res.setFocusable(true);
        return res;
    }

    private JPanel createStockMarketPanel() {
        JPanel res = new StockMarket(this);
        res.setPreferredSize(new Dimension(900, 700));
        res.setBackground(new Color(50, 232, 8));
        res.setFocusable(true);
        return res;
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }
}
