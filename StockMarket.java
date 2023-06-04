import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class StockMarket extends GamePanel implements ActionListener {
    private Timer t = new Timer(2000, this);
    private List<Double> oilHistory;
    private List<Double> goldHistory;
    private List<Double> diamondHistory;
    private long[] sharesBought = new long[] { 0L, 0L, 0L };
    private double[] sumPrice = new double[] { 0.0, 0.0, 0.0 };
    private String selected = "oil";
    private int ind = 0;

    public StockMarket(MainFrame f) {
        super(f);
        oilHistory = new ArrayList<>();
        diamondHistory = new ArrayList<>();
        goldHistory = new ArrayList<>();
        oilHistory.add(10.0);
        goldHistory.add(1000.0);
        diamondHistory.add(100000.0);
        for (int i = 0; i < 30; i++) {
            createNewStat();
        }
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long money = (((MainGamePanel) container.mainPanel).money);
        g.setColor(new Color(27, 133, 3, 123));
        g.fillRect(0, 0, 50, 50);
        GameUtils.drawImage("closeIcon.png", g, 50, 50);

        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Teko", Font.BOLD, 40));
        g.drawString("Money: $" + GameUtils.format(((MainGamePanel) container.mainPanel).money), 330, 40);

        int index = 1;
        for (String s : List.of("oil", "gold", "diamond")) {
            if (s.equals(selected)) {
                GameUtils.drawImage(s + "Blue.png", g, 100, 90, 25, index * 150, this);
            } else {
                GameUtils.drawImage(s + "Norm.png", g, 100, 90, 25, index * 150, this);
            }
            index++;
        }
        List<Double> history = getHistoryList(selected);
        double currPrice = history.get(history.size() - 1);
        long halfShares = (long) (money / 2 / currPrice);
        long quarterShares = (long) (money / 4 / currPrice);
        long allShares = (long) (money / currPrice);
        g.setColor(new Color(211, 237, 12));
        if (money < currPrice) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(50, 610, 150, 75);
        g.setColor(new Color(211, 237, 12));
        if (quarterShares == 0) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(250, 610, 150, 75);
        g.setColor(new Color(211, 237, 12));
        if (halfShares == 0) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(450, 610, 150, 75);
        g.setColor(new Color(211, 237, 12));
        if (money < currPrice) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(650, 610, 150, 75);
        g.setColor(new Color(211, 237, 12));
        if (sharesBought[ind] == 0) {
            g.setColor(new Color(100, 100, 100));
        }
        g.fillRect(605, 190, 150, 75);
        g.setColor(Color.BLACK);
        g.drawString("Invest: ", 400, 600);
        g.setFont(new Font("Teko", Font.BOLD, 20));
        g.drawString("1 Share", 85, 630);
        g.drawString(String.format("($%.2f)", currPrice),
                85 - (currPrice > 1000 ? 20 : 0), 650);
        g.drawString("50% of money", 455, 630);
        g.drawString(
                String.format("%s share%s", (halfShares > 10000 ? GameUtils.format(halfShares) : halfShares + ""),
                        halfShares == 1 ? "" : "s"),
                455,
                650);
        g.drawString(String.format("($%s)",
                (halfShares * currPrice > 10000 ? GameUtils.format((long) (halfShares * currPrice))
                        : String.format("%.2f", halfShares * currPrice))),
                455, 670);
        g.drawString("25% of money", 255, 630);
        g.drawString(
                String.format("%s share%s",
                        (quarterShares > 10000 ? GameUtils.format(quarterShares) : quarterShares + ""),
                        quarterShares == 1 ? "" : "s"),
                255,
                650);
        g.drawString(String.format("($%s)",
                (quarterShares * currPrice > 10000 ? GameUtils.format((long) (quarterShares * currPrice))
                        : String.format("%.2f", quarterShares * currPrice))),
                255, 670);
        g.drawString("Max", 660, 630);
        g.drawString(
                String.format("%s share%s",
                        (allShares > 10000 ? GameUtils.format(allShares) : allShares + ""),
                        allShares == 1 ? "" : "s"),
                660,
                650);

        g.drawString(
                selected.substring(0, 1).toUpperCase() + selected.substring(1) + " shares bought: "
                        + GameUtils.format(sharesBought[ind]),
                550, 135);
        if (sharesBought[ind] > 0) {
            g.drawString(
                    String.format("Average original price of shares: $%s",
                            (sumPrice[ind] / sharesBought[ind] > 10000)
                                    ? GameUtils.format((long) (sumPrice[ind] / sharesBought[ind]))
                                    : String.format("%,.2f", sumPrice[ind] / sharesBought[ind])),
                    450,
                    180);
        }
        g.drawString("Sell All", 640, 210);
        drawGraph(g);
    }

    public void drawGraph(Graphics graph) {
        Graphics2D g = (Graphics2D) graph;
        g.setColor(new Color(0, 0, 0));
        int border = 150;
        Color graphColor = new Color(200, 120, 0);
        Color graphPointColor = new Color(0, 0, 0, 220);
        int pointWidth = 11;
        int yTicks = 10;
        List<Double> hist = getHistoryList(selected);
        int xTicks = Math.min(hist.size(), 30);
        double maxScore = 0;
        for (int i = hist.size() - 1; i > Math.max(0, hist.size() - 30); i--) {
            if (hist.get(i) > maxScore) {
                maxScore = hist.get(i);
            }
        }
        maxScore *= 1.5;

        double xScale = ((double) getWidth() - 2 * border) / (xTicks - 1);
        double yScale = ((double) getHeight() - 2 * border) / (maxScore - 1);

        List<Point> graphPoints = new ArrayList<>();
        for (int i = hist.size() - 1; i > Math.max(0, hist.size() - 30); i--) {
            int x1 = (int) ((i - Math.max(0, hist.size() - 30)) * xScale + border);
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

        g.setFont(new Font("Teko", Font.PLAIN, 20));
        g.drawString(String.format("Current Price: $%,.2f", hist.get(hist.size() - 1)),
                190, 90);
        if (hist.size() > 30) {
            double avg = 0.0;
            double high = Double.MIN_VALUE;
            double low = Double.MAX_VALUE;
            for (int i = hist.size() - 1; i >= hist.size() - 30; i--) {
                avg += hist.get(i);
                high = Math.max(high, hist.get(i));
                low = Math.min(low, hist.get(i));
            }
            g.drawString(String.format("30 Day Rolling Average: $%,.2f", avg / 30), 190, 115);
            g.drawString(String.format("30 Day High: $%,.2f", high), 190, 140);
            g.drawString(String.format("30 Day Low: $%,.2f", low), 190, 165);

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
            ind = 0;
            repaint();
        } else if (GameUtils.isInside(e, 25, 125, 300, 400)) {
            selected = "gold";
            ind = 1;
            repaint();
        } else if (GameUtils.isInside(e, 25, 125, 450, 550)) {
            selected = "diamond";
            ind = 2;
            repaint();
        }
        MainGamePanel main = (MainGamePanel) (container.mainPanel);
        long money = main.money;
        double sharePrice = getHistoryList(selected).get(getHistoryList(selected).size() - 1);
        if (GameUtils.isInside(e, 50, 200, 610, 685) && money > sharePrice) {
            main.increaseMoney((long) (-1 * sharePrice));
            sharesBought[ind]++;
            sumPrice[ind] += sharePrice;
            repaint();
        } else if (GameUtils.isInside(e, 250, 400, 610, 685)) {
            long quarterShares = (long) (money / 4 / sharePrice);
            main.increaseMoney((long) (-1 * sharePrice * quarterShares));
            sharesBought[ind] += quarterShares;
            sumPrice[ind] += sharePrice * quarterShares;
            repaint();
        } else if (GameUtils.isInside(e, 450, 600, 610, 685)) {
            long halfShares = (long) (money / 2 / sharePrice);
            main.increaseMoney((long) (-1 * sharePrice * halfShares));
            sharesBought[ind] += halfShares;
            sumPrice[ind] += sharePrice * halfShares;
            repaint();
        } else if (GameUtils.isInside(e, 650, 800, 610, 685)) {
            long allShares = (long) (money / sharePrice);
            main.increaseMoney((long) (-1 * sharePrice * allShares));
            sharesBought[ind] += allShares;
            sumPrice[ind] += sharePrice * allShares;
            repaint();
        }

        if (GameUtils.isInside(e, 605, 755, 190, 265)) {
            main.increaseMoney((long) (sharePrice * sharesBought[ind]));
            sharesBought[ind] = 0;
            sumPrice[ind] = 0;
            repaint();
        }
    }

    public void createNewStat() {
        oilHistory.add(
                Math.max(oilHistory.get(oilHistory.size() - 1) * (1 + ((Math.random() - 0.505) / 5)), 1));
        goldHistory.add(
                Math.max(goldHistory.get(goldHistory.size() - 1)
                        * (1 + ((Math.random() - (goldHistory.get(goldHistory.size() - 1) > 50000 ? 0.51 : 0.49)) / 4)),
                        1));
        diamondHistory.add(
                Math.max(diamondHistory.get(diamondHistory.size() - 1)
                        * (1 + ((Math.random()
                                - (diamondHistory.get(diamondHistory.size() - 1) > 3000000 ? 0.52 : 0.45))
                                / 1.5)),
                        1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        createNewStat();
        repaint();
    }
}
