import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlatformerGame extends JPanel implements ActionListener, KeyListener {
    // Game loop timer
    private Timer timer;
    private final int DELAY = 10;

    // Player properties
    private int playerX = 50;
    private int playerY = 300;
    private int playerWidth = 40;
    private int playerHeight = 40;
    private int playerSpeed = 5;
    private int verticalSpeed = 0;
    private boolean isJumping = false;

    // Gravity and jump strength
    private final int GRAVITY = 1;
    private final int JUMP_STRENGTH = -15;

    // Platform properties
    private final Rectangle[] platforms = {
        new Rectangle(50, 350, 200, 10),
        new Rectangle(300, 300, 150, 10),
        new Rectangle(500, 250, 200, 10),
    };

    public PlatformerGame() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.CYAN);
        setFocusable(true);
        addKeyListener(this);

        // Start game loop
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the player
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

        // Draw platforms
        g.setColor(Color.GREEN);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }

        // Draw ground
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 380, 800, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Apply gravity
        verticalSpeed += GRAVITY;

        // Update player position
        playerY += verticalSpeed;

        // Check collision with platforms
        for (Rectangle platform : platforms) {
            if (playerY + playerHeight >= platform.y &&
                playerY + playerHeight <= platform.y + 10 &&
                playerX + playerWidth >= platform.x &&
                playerX <= platform.x + platform.width) {
                playerY = platform.y - playerHeight; // Align player with the platform
                isJumping = false;
                verticalSpeed = 0;
            }
        }

        // Check collision with the ground
        if (playerY + playerHeight >= 380) {
            playerY = 380 - playerHeight;
            isJumping = false;
            verticalSpeed = 0;
        }

        // Repaint the screen
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Move left
        if (key == KeyEvent.VK_LEFT) {
            playerX -= playerSpeed;
        }

        // Move right
        if (key == KeyEvent.VK_RIGHT) {
            playerX += playerSpeed;
        }

        // Jump
        if (key == KeyEvent.VK_SPACE && !isJumping) {
            verticalSpeed = JUMP_STRENGTH;
            isJumping = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optional: Stop player movement when keys are released
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Platformer Game");
        PlatformerGame game = new PlatformerGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}