import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SnakeStarter extends JPanel {
    private final int gridSize = 20;
    private final int cellSize = 25;
    private java.util.List<Point> snake;
    private Direction direction;
    private Timer timer;
    private Point food;
    private int score;
    private boolean gameOver;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public SnakeStarter() {
        snake = new ArrayList<>();
        // Initialize snake at center, 3 segments horizontally
        int centerX = gridSize / 2;
        int centerY = gridSize / 2;
        snake.add(new Point(centerX, centerY));
        snake.add(new Point(centerX - 1, centerY));
        snake.add(new Point(centerX - 2, centerY));
        direction = Direction.RIGHT;
        score = 0;
        gameOver = false;
        generateFood();

        // Set up timer to move snake every 150ms
        timer = new Timer(150, e -> {
            if (!gameOver) {
                moveSnake();
                repaint();
            }
        });
        timer.start();

        // Make panel focusable to receive key events
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        resetGame();
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (direction != Direction.DOWN) direction = Direction.UP;
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != Direction.UP) direction = Direction.DOWN;
                            break;
                        case KeyEvent.VK_LEFT:
                            if (direction != Direction.RIGHT) direction = Direction.LEFT;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != Direction.LEFT) direction = Direction.RIGHT;
                            break;
                    }
                }
            }
        });
    }

    private void generateFood() {
        Random rand = new Random();
        do {
            food = new Point(rand.nextInt(gridSize), rand.nextInt(gridSize));
        } while (snake.contains(food));
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head);
        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        // Check collision with walls
        if (newHead.x < 0 || newHead.x >= gridSize || newHead.y < 0 || newHead.y >= gridSize) {
            gameOver = true;
            timer.stop();
            return;
        }
        // Check collision with self
        if (snake.contains(newHead)) {
            gameOver = true;
            timer.stop();
            return;
        }
        snake.add(0, newHead);
        if (newHead.equals(food)) {
            score++;
            generateFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void resetGame() {
        snake.clear();
        int centerX = gridSize / 2;
        int centerY = gridSize / 2;
        snake.add(new Point(centerX, centerY));
        snake.add(new Point(centerX - 1, centerY));
        snake.add(new Point(centerX - 2, centerY));
        direction = Direction.RIGHT;
        score = 0;
        gameOver = false;
        generateFood();
        timer.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(22, 28, 39));
        g.fillRect(0, 0, gridSize * cellSize, gridSize * cellSize);
        // Draw snake
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
        // Draw food
        g.setColor(Color.RED);
        g.fillOval(food.x * cellSize, food.y * cellSize, cellSize, cellSize);
        // Draw score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        // Draw game over
        if (gameOver) {
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 24);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            String msg1 = "Game Over! Score: " + score;
            String msg2 = "Press R to restart";
            int x1 = (getWidth() - fm.stringWidth(msg1)) / 2;
            int x2 = (getWidth() - fm.stringWidth(msg2)) / 2;
            int y = getHeight() / 2;
            g.drawString(msg1, x1, y);
            g.drawString(msg2, x2, y + 30);
        }
    }

    // main method to create the window
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game Starter");
        SnakeStarter panel = new SnakeStarter();
        frame.add(panel);
        frame.setSize(525, 545); // gridSize * cellSize + some padding
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

