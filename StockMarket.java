import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class StockMarket extends GamePanel implements ActionListener {
    private Timer t = new Timer(1300, this);
    private List<Double> oilHistory;
    private List<Double> goldHistory;
    private List<Double> diamondHistory;
    private String selected = "oil";

    public StockMarket(MainFrame f) {
        super(f);
        oilHistory = new ArrayList<>();
        diamondHistory = new ArrayList<>();
        goldHistory = new ArrayList<>();
        oilHistory.add(10.0);
        goldHistory.add(500.0);
        diamondHistory.add(3000.0);
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("closeIcon.png", g, 50, 50);

        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.BOLD, 40));
        g.drawString("Money: $" + GameUtils.format(((MainGamePanel) container.mainPanel).money), 330, 40);

        int ind = 1;
        for (String s : List.of("oil", "gold", "diamond")) {
            if (s.equals(selected)) {
                GameUtils.drawImage(s + "Blue.png", g, 100, 90, 25, ind * 150, this);
            } else {
                GameUtils.drawImage(s + "Norm.png", g, 100, 90, 25, ind * 150, this);
            }
            ind++;
        }

        g.setColor(Color.BLACK);
        g.drawString("Invest: ", 400, 600);

        drawGraph(g);
    }

    public void drawGraph(Graphics graph) {
        Graphics2D g = (Graphics2D) graph;
        int border = 150;
        Color graphColor = new Color(200, 120, 0);
        Color graphPointColor = new Color(0, 0, 0, 220);
        int pointWidth = 15;
        int yTicks = 10;
        List<Double> hist = getHistoryList(selected);
        int xTicks = Math.min(hist.size(), 15);
        double maxScore = 0;
        for (int i = hist.size() - 1; i > Math.max(0, hist.size() - 15); i--) {
            if (hist.get(i) > maxScore) {
                maxScore = hist.get(i);
            }
        }
        maxScore *= 1.5;

        double xScale = ((double) getWidth() - 2 * border) / (xTicks - 1);
        double yScale = ((double) getHeight() - 2 * border) / (maxScore - 1);

        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = hist.size() - 1; i > Math.max(0, hist.size() - 15); i--) {
            int x1 = (int) ((i - Math.max(0, hist.size() - 15)) * xScale + border);
            int y1 = (int) ((maxScore - hist.get(i)) * yScale + border);
            graphPoints.add(0, new Point(x1, y1));
        }

        // x and y axes
        g.drawLine(border, getHeight() - border, border, border);
        g.drawLine(border, getHeight() - border, getWidth() - border, getHeight() - border);

        // ticks for y axis.
        for (int i = 0; i < yTicks; i++) {
            int y = getHeight() - (((i + 1) * (getHeight() - border * 2)) / yTicks + border);
            g.drawLine(border, y, pointWidth + border, y);
        }

        // ticks for x axis
        for (int i = 0; i < xTicks - 1; i++) {
            int x = (i + 1) * (getWidth() - border * 2) / (xTicks - 1) + border;
            g.drawLine(x, getHeight() - border, x, getHeight() - border - pointWidth);
        }

        g.setColor(graphColor);
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            Point thisPoint = graphPoints.get(i);
            Point nextPoint = graphPoints.get(i + 1);
            g.drawLine(thisPoint.x, thisPoint.y, nextPoint.x, nextPoint.y);
        }

        g.setColor(graphPointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            g.fillOval(x, y, pointWidth, pointWidth);
        }
    }

    public List<Double> getHistoryList(String s) {
        if (s.equals("gold")) {
            return goldHistory;
        }
        if (s.equals("diamond")) {
            return diamondHistory;
        }
        return oilHistory;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (GameUtils.isInside(e, 0, 50, 0, 50)) {
            navigateTo("game");
            MainGamePanel.setPaused(false);
        }
        if (GameUtils.isInside(e, 25, 125, 150, 250)) {
            selected = "oil";
            repaint();
        } else if (GameUtils.isInside(e, 25, 125, 300, 400)) {
            selected = "gold";
            repaint();
        } else if (GameUtils.isInside(e, 25, 125, 450, 550)) {
            selected = "diamond";
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        oilHistory.add(
                Math.min(Math.max(oilHistory.get(oilHistory.size() - 1) * (1 + ((Math.random() - 0.5) / 5)), 0), 20));
        diamondHistory.add(
                Math.min(Math.max(diamondHistory.get(diamondHistory.size() - 1) * (1 + ((Math.random() - 0.5) / 5)), 0),
                        20000));
        goldHistory.add(
                Math.min(Math.max(goldHistory.get(goldHistory.size() - 1) * (1 + ((Math.random() - 0.5) / 5)), 0),
                        2000));
        repaint(150, 0, 750, 700);
    }
}
