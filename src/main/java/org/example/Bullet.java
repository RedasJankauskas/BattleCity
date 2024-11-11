package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet {
    private int x, y;
    private int speed = 6;
    private String direction;
    private boolean active;
    private BufferedImage bulletImage;

    int screenWidth, screenHeight;

    public Bullet(int x, int y, String direction, int screenWidth, int screenHeight) {
        this.x = x;
        this.y = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.direction = direction;
        this.active = true;
        loadBulletImage();
    }

    private void loadBulletImage() {
        try {
            bulletImage = ImageIO.read(new FileInputStream("Bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        switch (direction) {
            case "up":
                y -= speed;
                break;
            case "down":
                y += speed;
                break;
            case "left":
                x -= speed;
                break;
            case "right":
                x += speed;
                break;
        }

        if (x < 0 || y < 0 || x > screenWidth || y > screenHeight) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        if (bulletImage != null && active) {
            g.drawImage(bulletImage, x, y, null);
        }
    }

    public boolean isActive() {
        return active;
    }

    public boolean enemyCollision(Enemy enemy) {
        return x == enemy.getX() && y == enemy.getY();
    }
}
