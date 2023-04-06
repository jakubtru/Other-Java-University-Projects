package pl.agh.lab10;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    LocalTime time = LocalTime.now();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        ClockThread clock = new ClockThread();
        clock.start();

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);

        g2d.drawOval(-162, -169, 330, 330);
        g2d.fillOval(-162, -169, 330, 330);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(-147, -155, 300, 300);
        g2d.fillOval(-147, -155, 300, 300);
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 1; i < 13; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 12 * i);
            Point2D src = new Point2D.Float(-5, -125);
            Point2D trg = new Point2D.Float();
            at.transform(src, trg);
            g2d.drawString(Integer.toString(i), (int) trg.getX(), (int) trg.getY());
        }

        for (int i = 1; i < 61; i++) {
            g2d.setColor(Color.CYAN);
            if (i % 5 == 0) {
                g2d.setColor(Color.RED);
            }
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 60 * i);
            Point2D src = new Point2D.Float(0, -100);
            Point2D trg = new Point2D.Float();
            at.transform(src, trg);
            g2d.drawString(".", (int) trg.getX(), (int) trg.getY());
        }

        AffineTransform saveAT = g2d.getTransform();
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        //wskazówka godzinowa zmienia położenie nie tylko na pełne godziny
        g2d.rotate(time.getHour() % 12 * 2 * Math.PI / 12 + time.getMinute() % 60 * 2 * Math.PI / 60 / 12);
        g2d.drawLine(0, 0, 0, -60);
        g2d.setTransform(saveAT);


        g2d.rotate(time.getMinute() % 60 * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -75);
        g2d.setTransform(saveAT);

        g2d.rotate(time.getSecond() % 60 * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -90);
        g2d.setTransform(saveAT);

    }

    class ClockThread extends Thread {
        @Override
        public void run() {
            while (true) {
                time = LocalTime.now();

                repaint();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
