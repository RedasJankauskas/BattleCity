package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Tank {
    private int x = 50;
    private int y = 50;
    private int speed;
    public BufferedImage tankUpImage, tankDownImage, tankLeftImage, tankRightImage;
    public String direction;

    GamePanel gamePanel;
    KeyHandler keyH;

    public Tank(GamePanel gamePanel, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        setDefaultValues();
        loadImages();
    }

    private void loadImages() {
        try {
            tankUpImage = ImageIO.read(new FileInputStream("TankUp.png"));
            tankDownImage = ImageIO.read(new FileInputStream("TankDown.png"));
            tankLeftImage = ImageIO.read(new FileInputStream("TankLeft.png"));
            tankRightImage = ImageIO.read(new FileInputStream("TankRight.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void setDefaultValues() {
        this.x = 50;
        this.y = 50;
        this.speed = 3;
        direction = "down";
    }

    public void update() {
        int newX = x;
        int newY = y;

        if (keyH.keyUp) {
            newY -= speed;
            direction = "up";
        } else if (keyH.keyDown) {
            newY += speed;
            direction = "down";
        } else if (keyH.keyLeft) {
            newX -= speed;
            direction = "left";
        } else if (keyH.keyRight) {
            newX += speed;
            direction = "right";
        }

        if (!gamePanel.map.isWallAt(newX, newY)) {
            x = newX;
            y = newY;
        }

        if (keyH.keyShoot) {
            shoot();
        }
    }

    public void shoot() {
        Bullet bullet = new Bullet(x, y, direction, gamePanel.screenWidth, gamePanel.screenHeight);
        gamePanel.bullets.add(bullet);
    }

    public void draw(Graphics g) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = tankUpImage;
                break;
            case "down":
                image = tankDownImage;
                break;
            case "left":
                image = tankLeftImage;
                break;
            case "right":
                image = tankRightImage;
                break;
        }

        g.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
