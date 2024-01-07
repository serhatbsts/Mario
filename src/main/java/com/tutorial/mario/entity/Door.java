package com.tutorial.mario.entity;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;

import java.awt.*;

public class Door extends Entity{
    public Door(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.door.getBufferedImage(), x,y,width,height,null);

    }

    @Override
    public void tick() {
    }
}
