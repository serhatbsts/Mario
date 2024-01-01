package com.tutorial.mario.gfx.gui;

import com.tutorial.mario.Game;

import java.awt.*;

public class Launcher {
    public Button[] buttons;
    public Launcher(){
        buttons = new Button[2];

        buttons[0] = new Button(100,100,100,100,"START GAME");
        buttons[1] = new Button(200,200,100,100,"EXIT GAME");

    }
    public void render(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(0,0, Game.getFrameWidht(),Game.getFrameHeight());

        for (int i=0;i< buttons.length;i++) {
            buttons[i].render(g);
        }

    }
}
