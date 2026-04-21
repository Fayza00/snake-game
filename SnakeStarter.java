import java.awt.*;
import javax.swing.*;


public class SnakeStarter extends JPanel {
    private final int gridSize = 20;
    private final int cellSize = 25;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(22, 28, 39));
        g.fillRect(0, 0, gridSize * cellSize, gridSize * cellSize);
        // draw a green 3-segment snake near the center
        g.setColor(new Color(r: 0, g: 255, b:0));
        int centerX = gridSize / 2 * cellSize;
        int centerY = gridSize / 2 * cellSize;
        g.fillRect(centerX, CenterY, cellSize, cellSize); // head
        g.fillRect(centerX - cellSize, centerY, cellSize, cellSize); // body
        g.fillRect(centerX - 2 * cellSize, centerY, cellSize, cellSize); // tail


    }


    // main method to create the window
    public static void main(String[] args) {
        JFrame frame = new  JFrame(title: "Snake Game Starter");
        SnakeStarter panel = new SnakeStarter();
        frame.add(panel);
        frame.setSize(width: 525, height: 545); gridSize * cellSize + some padding
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(b: true);

