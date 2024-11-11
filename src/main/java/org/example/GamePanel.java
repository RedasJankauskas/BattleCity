package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {

    public int tileSize = 48;
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = maxScreenCol * tileSize;
    public int screenHeight = maxScreenRow * tileSize;

    Map map = new Map(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Tank tank = new Tank(this, keyH);
    ArrayList<Bullet> bullets = new ArrayList<>();

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;

        while (gameThread != null) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        tank.update();

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();

            for (int row = 0; row < map.enemies.length; row++) {
                for (int col = 0; col < map.enemies[0].length; col++) {
                    Enemy enemy = map.enemies[row][col];
                    if (enemy != null && bullet.enemyCollision(enemy)) {
                        iterator.remove();
                        map.enemies[row][col] = null;
                        break;
                    }
                }
            }

            if (!bullet.isActive()) {
                iterator.remove();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        map.draw(g);
        tank.draw(g);

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        g.dispose();
    }
}
