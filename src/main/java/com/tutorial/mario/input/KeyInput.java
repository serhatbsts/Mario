package com.tutorial.mario.input;

import com.tutorial.mario.Game;
import com.tutorial.mario.entity.Entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        //keylistenerden override edşldiğinden metot burada yazılı olmak zorundu ama  gerekli olmadığından herhangi bir tuş ataması yapılmadı.
    }


    //Tuşa basılı tutuğunda yapılacak işlemin fonksiyonudur.
    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        for (Entity en: Game.handler.entity){
            switch (key){
                case KeyEvent.VK_W :
                    if(!en.jumping) {
                        en.jumping=true;
                        en.gravtiy=10.0;
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
//Tuşa bıraktığımızda yapılacak işlemin fonksiyonudur.

    @Override
    public void keyReleased(KeyEvent e) {
        int key=e.getKeyCode();
        for (Entity en: Game.handler.entity){
            switch (key){
                case KeyEvent.VK_W :
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_S :
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
