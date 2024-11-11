package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean keyUp, keyDown, keyRight, keyLeft, keyShoot;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W)
            keyUp = true;
        if (code == KeyEvent.VK_S)
            keyDown = true;
        if (code == KeyEvent.VK_D)
            keyRight = true;
        if (code == KeyEvent.VK_A)
            keyLeft = true;
        if (code == KeyEvent.VK_E)
            keyShoot = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) keyUp = false;
        if (code == KeyEvent.VK_S) keyDown = false;
        if (code == KeyEvent.VK_D) keyRight = false;
        if (code == KeyEvent.VK_A) keyLeft = false;
        if (code == KeyEvent.VK_E) keyShoot = false;
    }
}
