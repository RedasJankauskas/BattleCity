package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Map {
    GamePanel gamePanel;
    Wall[][] walls;
    Enemy[][] enemies;
    private static final String MAP_FILE = "map.txt";
    private BufferedImage wallImage;
    private BufferedImage enemyImage;

    public Map(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        walls = new Wall[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
        enemies = new Enemy[gamePanel.maxScreenRow][gamePanel.maxScreenCol]; // initialize enemy grid
        loadImages();
        loadMapFromFile();
    }

    private void loadImages() {
        try {

            wallImage = ImageIO.read(new FileInputStream("Wall.png"));
            enemyImage = ImageIO.read(new FileInputStream("Enemy.png"));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private void loadMapFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MAP_FILE))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null && row < gamePanel.maxScreenRow) {
                for (int col = 0; col < line.length() && col < gamePanel.maxScreenCol; col++) {
                    char cell = line.charAt(col);

                    if (cell == '1') {  // Wall
                        walls[row][col] = new Wall();
                        walls[row][col].image = wallImage;
                        walls[row][col].x = col * gamePanel.tileSize;
                        walls[row][col].y = row * gamePanel.tileSize;

                    } else if (cell == '2') {  // Enemy
                        enemies[row][col] = new Enemy();
                        enemies[row][col].image = enemyImage;
                        enemies[row][col].x = col * gamePanel.tileSize;
                        enemies[row][col].y = row * gamePanel.tileSize;
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWallAt(int x, int y) {
        int col = x / gamePanel.tileSize;
        int row = y / gamePanel.tileSize;

        if (row >= 0 && row < gamePanel.maxScreenRow && col >= 0 && col < gamePanel.maxScreenCol) {
            return walls[row][col] != null;
        }
        return false;
    }

    public boolean isEnemyAt(int x, int y) {
        int col = x / gamePanel.tileSize;
        int row = y / gamePanel.tileSize;

        if (row >= 0 && row < gamePanel.maxScreenRow && col >= 0 && col < gamePanel.maxScreenCol) {
            return enemies[row][col] != null;
        }
        return false;
    }

    public void removeEnemy(int x, int y) {
        int col = x / gamePanel.tileSize;
        int row = y / gamePanel.tileSize;

        if (row >= 0 && row < gamePanel.maxScreenRow && col >= 0 && col < gamePanel.maxScreenCol) {
            enemies[row][col] = null;
        }
    }

    public void draw(Graphics g) {
        for (int row = 0; row < gamePanel.maxScreenRow; row++) {
            for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                if (walls[row][col] != null) {
                    g.drawImage(walls[row][col].image, walls[row][col].x, walls[row][col].y, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
        }

        for (int row = 0; row < gamePanel.maxScreenRow; row++) {
            for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                if (enemies[row][col] != null) {
                    g.drawImage(enemies[row][col].image, enemies[row][col].x, enemies[row][col].y, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
        }
    }
}
