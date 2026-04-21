import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setSize(600, 600);
        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel {
    private static final int CELL_SIZE = 30;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private List<Point> snake;

    public GamePanel() {
        setBackground(Color.DARK_GRAY);
        initializeSnake();
    }

    private void initializeSnake() {
        snake = new ArrayList<>();
        // Starting snake near center, facing right: positions (8,10), (9,10), (10,10) where (10,10) is head
        snake.add(new Point(8, 10));
        snake.add(new Point(9, 10));
        snake.add(new Point(10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawSnake(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for (int x = 0; x <= BOARD_WIDTH * CELL_SIZE; x += CELL_SIZE) {
            g.drawLine(x, 0, x, BOARD_HEIGHT * CELL_SIZE);
        }
        for (int y = 0; y <= BOARD_HEIGHT * CELL_SIZE; y += CELL_SIZE) {
            g.drawLine(0, y, BOARD_WIDTH * CELL_SIZE, y);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point segment : snake) {
            g.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}