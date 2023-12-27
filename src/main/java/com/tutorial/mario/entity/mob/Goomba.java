package com.tutorial.mario.entity.mob;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

import java.awt.*;

public class Goomba extends Entity {
    public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    public void render(Graphics g) {
        if (facing==0){
            g.drawImage(Game.goomba[frame+5].getBufferedImage(), x,y,width,height,null);
        }else if (facing==1){
            g.drawImage(Game.goomba[frame].getBufferedImage(), x,y,width,height,null);
        }

    }
    public void tick() {

        x+=velX;
        y+=velY;
        for (Tile t : handler.tile) {
            if (t.isSolid()){
                if (getBoundsBottom().intersects(t.getBounds())) {

                    setVelY(0);
                    if (falling) falling = false;
                } else {
                    if (!falling) {
                        gravity = 0.8;
                        falling = true;
                    }
                }

                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(2);
                }

                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-2);
                }
            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }
        if (velX!=0){
            frameDelay++;
            if (frameDelay>=3) {
                frame++;
                if (frame>=5){
                    frame=0;
                }
                frameDelay=0;
            }

        }

    }
    }
