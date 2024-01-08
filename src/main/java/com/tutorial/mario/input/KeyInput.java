package com.tutorial.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.tutorial.mario.Game;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

public class KeyInput implements KeyListener {

    // Bir tuşa basıldığında tetiklenen metod
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Oyuncu hareketlerini kontrol etme
        for (Entity en : Game.handler.entity) {
            if (en.getId()== Id.player){
                switch (key) {
                    case KeyEvent.VK_W:
                                    if (!en.jumping ) {
                                        en.jumping = true;
                                        en.gravity = 11.0;
                                    }
                        break;
                    case KeyEvent.VK_S:
                        for (int j=0;j<Game.handler.tile.size();j++) {
                            Tile t = Game.handler.tile.get(j);
                        }
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(-5);
                        en.facing=0;//oyuncunun yönünü gittiği yöne doğru çevirir.
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        en.facing=1;//oyuncunun yönünü gittiği yöne doğru çevirir.
                        break;
                    case KeyEvent.VK_Q://deneme amaçlı yapılmıştır
                        en.die();
                        break;
                }
            }

        }
    }

    // Bir tuş bırakıldığında tetiklenen metod
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Oyuncu hareketlerini kontrol etme
        for (Entity en : Game.handler.entity) {
            if (en.getId()==Id.player){
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
    }

    // Tuşa basılı tutulduğunda tetiklenen metod (kullanılmıyor)
    public void keyTyped(KeyEvent e) {
        // kullanılmıyor
    }
}
