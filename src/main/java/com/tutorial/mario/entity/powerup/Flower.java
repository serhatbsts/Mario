package com.tutorial.mario.entity.powerup;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;

import java.awt.*;
//kullanmaya gerek yok
public class Flower extends Entity {
    public Flower(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {
      // g.drawImage(Game.flower.getBufferedImage(), getX(),getY(),getWidth(),getHeight(),null);

    }

    @Override
    public void tick() {

    }
}
