package com.tutorial.mario.entity.powerup;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class PowerStar extends Entity {

    private Random random;
    public PowerStar(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        random = new Random();

        int dir = random.nextInt(2);

        switch (dir){
            case 0:
                setVelX(-4);
                break;
            case 1:
                setVelX(4);
                break;
        }

        falling = true;
        gravity = 0.17;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.star.getBufferedImage(),getX(),getY(),getWidth(),getHeight(),null);

    }

    @Override
    public void tick() {

        for (Tile t: handler.tile) {
            if (t.isSolid()) {
                if (getBoundsBottom().intersects(t.getBounds())) {
                    jumping = true;
                    gravity = 8.0;
                }

                if (getBoundsLeft().intersects(t.getBounds())) setVelX(4);
                if (getBoundsRight().intersects(t.getBounds())) setVelX(-4);
            }
        }
        if (jumping) {
            gravity -= 0.17;
            setVelY((int) -gravity);
            if (gravity <= 0.5) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += 0.17;
            setVelY((int) gravity);
        }

    }
}
