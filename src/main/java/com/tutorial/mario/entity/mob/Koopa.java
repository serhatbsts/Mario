package com.tutorial.mario.entity.mob;

import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.states.KoopaState;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;
//kaplumboğa kabuğu

public class Koopa extends Entity {
    private Random random;
    private int shellCount;
    public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        random = new Random();
        int dir = random.nextInt(2);

        switch (dir){
            case 0:
                setVelX(-2);
                facing = 0;
                break;
            case 1:
                setVelX(2);
                facing = 1;
                break;
        }

        koopaState = KoopaState.WALKING;
    }

    @Override
    public void render(Graphics g) {

       if (koopaState==KoopaState.WALKING) g.setColor(new Color(39,227,51));
       else g.setColor(new Color(0,128,0));

        g.fillRect(getX(),getY(),width,height);
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        if (koopaState==KoopaState.SHELL) {
            setVelX(0);

            shellCount++;
            if (shellCount>=300) {
                shellCount = 0;

                koopaState = KoopaState.WALKING;
            }
            if (koopaState==KoopaState.WALKING||koopaState==KoopaState.SPINNING) {
                shellCount = 0;
                if (velX==0){
                    int dir = random.nextInt(2);

                    switch (dir){
                        case 0:
                            setVelX(-2);
                            facing = 0;
                            break;
                        case 1:
                            setVelX(2);
                            facing = 1;
                            break;
                    }
                }
            }
        }
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
                   if (koopaState==KoopaState.SPINNING) setVelX(10);
                   else setVelX(2);
                    facing=1;//facing değerleri olmayabilir
                }

                if (getBoundsRight().intersects(t.getBounds())) {
                    if (koopaState==KoopaState.SPINNING) setVelX(-10);
                    else setVelX(-2);
                    facing=0;//facing değerleri olmayabilir
                }
            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);


        }
    }
}
