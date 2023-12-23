package com.tutorial.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.tutorial.mario.Game;
import com.tutorial.mario.entity.Entity;

public class KeyInput implements KeyListener {

    // Bir tuşa basıldığında tetiklenen metod
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Oyuncu hareketlerini kontrol etme
        for (Entity en : Game.handler.entity) {
            switch (key) {
                case KeyEvent.VK_W:
                    if (!en.jumping) {
                        en.jumping = true;
                        en.gravity = 10.0;
                    }
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(-5);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(5);
                    break;
            }
        }
    }

    // Bir tuş bırakıldığında tetiklenen metod
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Oyuncu hareketlerini kontrol etme
        for (Entity en : Game.handler.entity) {
            switch (key) {
                case KeyEvent.VK_W:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_S:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(0);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(0);
                    break;
            }
        }
    }

    // Tuşa basılı tutulduğunda tetiklenen metod (kullanılmıyor)
    public void keyTyped(KeyEvent e) {
        // kullanılmıyor
    }
}
