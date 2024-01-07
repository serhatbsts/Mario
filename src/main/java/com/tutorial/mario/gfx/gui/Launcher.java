package com.tutorial.mario.gfx.gui;

import com.tutorial.mario.Game;

import java.awt.*;

public class Launcher {
    public Button[] buttons;
    public Launcher(){
        buttons = new Button[1];

        buttons[0] = new Button(700,400,320,90,"START GAME");
    }
    public void render(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, Game.getFrameWidht(),Game.getFrameHeight());

        for (int i=0;i< buttons.length;i++) {
            buttons[i].render(g);
        }

    }
}
