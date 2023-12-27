package com.tutorial.mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

public class Player extends Entity {

    private int frame=0;
    private int frameDelay=0;
    private boolean animate=false;


    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    // Grafik çizme metodu
    public void render(Graphics g) {
       /*
        oyuncuya ait bilgiler burada deneme iiçin yazılmıştır
       grafikler kullanılmaya başlayımca devre dışı bırakılacaktır.

        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);

        */


        //bu kısım oyuncu grafiği ile ilgilidir yeni grafikler eklendiğinde düzeltilecektir
        if (facing==0){
            g.drawImage(Game.player[frame+5].getBufferedImage(), x,y,width,height,null);
        }else if (facing==1){
            g.drawImage(Game.player[frame].getBufferedImage(), x,y,width,height,null);
        }

    }

    // Güncelleme metodu
    public void tick() {
        x += velX;
        y += velY;

        // Sınırları aşmamak için kontrol

        if (y + height >= 680) y = 680 - height; // Width / (14 * 10 * Scale) = 714.2857142857

        //oyuncunun hareket edip etmediğini kontrol eder.
        if (velX!=0){
            animate=true;
        }else {
            animate=false;
        }
        // Handler'daki her bir Tile için kontrol
        for (Tile t : handler.tile) {
            if (!t.solid) break;
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
                } else {
                    if (!falling && !jumping) {
                        gravity = 0.8;
                        falling = true;
                    }
                }

                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() + t.width;
                }

                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() - t.width;
                }
            }

        }
        //forlar yalnış olabilir grafik işlemlerini yaptıktan sonra tekrar kontrol et.
        for (int i=0;i<handler.entity.size();i++){
            Entity e = handler.entity.get(i);

            if (e.getId()==Id.mushroom){
                if (getBounds().intersects(e.getBounds())){
                    int tpX=getX();
                    int tpY=getY();
                    width*=2;
                    height*=2;
                    setX(tpX-width);
                    setY(tpY-height);
                    e.die();
                }
            }else if(e.getId()==getId().goomba){
                if (getBounds().intersects(e.getBounds())){
                    die();
                }
            }
        }

        if (jumping == true) {
            gravity -= 0.1;
            setVelY((int) -gravity);
            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }
        //oyuncu çerçevesi ile ilgili kodlar grafikler eklendiğinde düzeltilecektir.
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
