import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private ArrayList<Point> snake;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private Timer timer;
    private final int UNIT_SIZE = 20;
    private final int GAME_UNITS = 600 / UNIT_SIZE;

    public GamePanel() {
        snake = new ArrayList<>();
        snake.add(new Point(GAME_UNITS / 2, GAME_UNITS / 2)); // Start in center
        direction = 1; // Start moving right
        timer = new Timer(150, this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * UNIT_SIZE, p.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head);
        switch (direction) {
            case 0: newHead.y--; break; // up
            case 1: newHead.x++; break; // right
            case 2: newHead.y++; break; // down
            case 3: newHead.x--; break; // left
        }
        // Wrap around
        if (newHead.x < 0) newHead.x = GAME_UNITS - 1;
        if (newHead.x >= GAME_UNITS) newHead.x = 0;
        if (newHead.y < 0) newHead.y = GAME_UNITS - 1;
        if (newHead.y >= GAME_UNITS) newHead.y = 0;
        snake.add(0, newHead);
        snake.remove(snake.size() - 1); // Remove tail, since no food yet
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && direction != 1) direction = 3;
        else if (key == KeyEvent.VK_RIGHT && direction != 3) direction = 1;
        else if (key == KeyEvent.VK_UP && direction != 2) direction = 0;
        else if (key == KeyEvent.VK_DOWN && direction != 0) direction = 2;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}