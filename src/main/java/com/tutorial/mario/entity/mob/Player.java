package com.tutorial.mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;

import com.tutorial.mario.tile.Tile;

public class Player extends Entity {

    private int invicibilityTime = 0;
    private Random random;
    private boolean invincible=false;

    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        random = new Random();
    }

    // Grafik çizme metodu
    public void render(Graphics g) {


        if (facing==0){
            g.drawImage(Game.player[4+frame].getBufferedImage(), x,y,width,height,null);
        }else if (facing==1){
            g.drawImage(Game.player[frame].getBufferedImage(), x,y,width,height,null);
        }

    }

    // Güncelleme metodu
    public void tick() {
        x += velX;
        y += velY;




        // Handler'daki her bir Tile için kontrol
        for (Tile t : handler.tile) {
            if (t.isSolid()) {

            if (t.getId() == Id.wall) {
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    if (jumping) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                }

                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    if (falling) falling = false;
                } else if (!falling && !jumping) {
                    falling = true;
                    gravity = 0.8;
                }

                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() + width+12;
                }

                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() - width;
                }
            }
            }


        }

        for (int i=0;i<handler.entity.size();i++){
            Entity e = handler.entity.get(i);



           if(e.getId()==getId().goomba){
                if (invincible) e.die();
                    else {
                    if(getBoundsBottom().intersects(e.getBoundsTop())){
                      e.die();
                    } else if (getBounds().intersects(e.getBounds())){
                            die();
                    }
                    }
            }else if(e.getId()==Id.coin) {
                if (getBounds().intersects(e.getBounds())&&e.getId()==Id.coin){
                    Game.coins++;
                    e.die();

                }
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
        //oyuncu çerçevesi ile ilgili kodlar grafikler eklendiğinde düzeltilecektir.
        if (velX!=0){
            frameDelay++;
            if (frameDelay>=10) {
                frame++;
                if (frame>3){
                    frame=0;
                }
                frameDelay=0;
            }
        }

        if (invincible) {
            invicibilityTime++;
            if (invicibilityTime>=600) {
                invincible = false;
                invicibilityTime = 0;
            }
            if (velX==5) setVelX(8);
            else if (velX==-5) setVelX(-8);
        }else {
            if (velX==8) setVelX(5);
            else if (velX==-8) setVelX(-5);
        }
    }
}
