import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];

    private boolean left = false, right = false, up = false, down = false;

    private ImageIcon headUp, headDown, headLeft, headRight, tail;

    private int lengthOfSnake = 3;

    private Timer timer;
    private int delay = 100;

    private int moves = 0, scores = 0;

    private int[] fruitXpos = new int[34]; // 850 / 25
    private int[] fruitYpos = new int[24]; // 625 / 25

    private Random random = new Random();

    private int xpos = random.nextInt(33), ypos = random.nextInt(23);

    private ImageIcon fruitImage;
    private ImageIcon titleImage;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        fruitXpos[0] = 25;
        for (int i = 1; i < 34; i++) {
            fruitXpos[i] = fruitXpos[i - 1] + 25;
        }
        fruitYpos[0] = 75;
        for (int i = 1; i < 24; i++) {
            fruitYpos[i] = fruitYpos[i - 1] + 25;
        }

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (moves == 0) {
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
        }

        // Display title
        titleImage = new ImageIcon("title.png");
        titleImage.paintIcon(this, g, 25, 5);

        // Display border
        g.setColor(Color.DARK_GRAY);
        g.drawRect(24, 74, 851, 577);

        // Display background
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);

        // Score and Length
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 12));
        g.drawString("Scores: " + scores, 780, 35);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 12));
        g.drawString("Length: " + lengthOfSnake, 780, 50);

        // Initial position of head image
        headRight = new ImageIcon("headRight.png");
        headRight.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);

        for (int i = 0; i < lengthOfSnake; i++) {
            // i == 0 means we are in head position and hence updating the head
            if (i == 0 && right) {
                headRight = new ImageIcon("headRight.png");
                headRight.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
            if (i == 0 && left) {
                headLeft = new ImageIcon("headLeft.png");
                headLeft.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
            if (i == 0 && up) {
                headUp = new ImageIcon("headUp.png");
                headUp.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
            if (i == 0 && down) {
                headDown = new ImageIcon("headDown.png");
                headDown.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }

            // i other than 0 means we are in tail position
            if (i != 0) {
                tail = new ImageIcon("tail.png");
                tail.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }

            fruitImage = new ImageIcon("fruit.png");
            if (fruitXpos[xpos] == snakeXlength[0] && fruitYpos[ypos] == snakeYlength[0]) {
                scores += 5;
                lengthOfSnake++;
                xpos = random.nextInt(33);
                ypos = random.nextInt(23);
            }
            fruitImage.paintIcon(this, g, fruitXpos[xpos], fruitYpos[ypos]);
        }

        for (int i = 1; i < lengthOfSnake; i++) {
            if (snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]) {
                right = false;
                left = false;
                up = false;
                down = false;

                g.setColor(Color.red);
                g.setFont(new Font("arial", Font.BOLD, 40));
                g.drawString("Game Over! Score: " + scores, 250, 300);

                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press ENTER to RESTART the game.", 250, 340);
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.restart();
        if (right) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeYlength[i + 1] = snakeYlength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeXlength[i] += 25;
                }
                else {
                    snakeXlength[i] = snakeXlength[i - 1];
                }
                if (snakeXlength[i] > 850) {
                    snakeXlength[i] = 25;
                }
            }
            repaint();
        }
        if (left) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeYlength[i + 1] = snakeYlength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeXlength[i] -= 25;
                }
                else {
                    snakeXlength[i] = snakeXlength[i - 1];
                }
                if (snakeXlength[i] < 25) {
                    snakeXlength[i] = 850;
                }
            }
            repaint();
        }
        if (up) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeXlength[i + 1] = snakeXlength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeYlength[i] -= 25;
                }
                else {
                    snakeYlength[i] = snakeYlength[i - 1];
                }
                if (snakeYlength[i] < 75) {
                    snakeYlength[i] = 625;
                }
            }
            repaint();
        }
        if (down) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeXlength[i + 1] = snakeXlength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeYlength[i] += 25;
                }
                else {
                    snakeYlength[i] = snakeYlength[i - 1];
                }
                if (snakeYlength[i] > 625) {
                    snakeYlength[i] = 75;
                }
            }
            repaint();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            if (!left) {
                right = true;
            }
            else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            if (!right) {
                left = true;
            }
            else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            if (!down) {
                up = true;
            }
            else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            if (!up) {
                down = true;
            }
            else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moves = 0;
            scores = 0;
            lengthOfSnake = 3;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
