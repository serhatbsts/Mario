package com.tutorial.mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.states.BossState;
import com.tutorial.mario.states.PlayerState;
import com.tutorial.mario.tile.Tile;

public class Player extends Entity {

    private PlayerState state;
    private int pixelsTravelled = 0;

    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        state = PlayerState.SMALL;
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
            g.drawImage(Game.player[4+frame].getBufferedImage(), x,y,width,height,null);
        }else if (facing==1){
            g.drawImage(Game.player[frame].getBufferedImage(), x,y,width,height,null);
        }

    }

    // Güncelleme metodu
    public void tick() {
        x += velX;
        y += velY;


        // Sınırları aşmamak için kontrol

       // if (y + height >= 680) y = 680 - height; // Width / (14 * 10 * Scale) = 714.2857142857

        //oyuncunun hareket edip etmediğini kontrol eder.
   /*     if (velX!=0){
            animate=true;
        }else {
            animate=false;
        }*/
        // Handler'daki her bir Tile için kontrol
        for (Tile t : handler.tile) {
            if (t.isSolid() && !goingDownPipe) {
           /*
            if (t.getId() == Id.wall) { //yalnış yazmış olabilirim grafiklerden sonra tekrar bakılacak
           * */
                //&&t.getId()==Id.coin bu ifadeyi koyduğun yerleri tekrar kontrol et sonrasında
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    if (jumping&&!goingDownPipe) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                    if (t.getId() == Id.powerUp) {
                        if (getBoundsTop().intersects(t.getBounds())) t.activated = true;
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
                switch (e.getType()) {
                    case 0:
                        if (getBounds().intersects(e.getBounds())){
                            int tpX=getX();
                            int tpY=getY();
                            width+=(width/3);
                            height+=(height/3);
                            setX(tpX-width);
                            setY(tpY-height);
                            if (state==PlayerState.SMALL) state = PlayerState.BIG;
                            e.die();
                        }
                        break;
                    case 1:
                        if (getBounds().intersects(e.getBounds())) {
                            Game.lives++;
                            e.die();
                        }
                }

            }else if(e.getId()==getId().goomba||e.getId()==Id.towerBoss){
                if(getBoundsBottom().intersects(e.getBoundsTop())){
                   if(e.getId()!=Id.towerBoss) e.die();
                   else if (e.attackable){
                       e.hp--;
                       e.falling = true;
                       e.gravity = 3.0;
                       e.bossState = BossState.RECOVERING;
                       e.attackable = false;
                       e.phaseTime = 0;

                       jumping = true;
                       falling = false;
                       gravity = 3.5;
                   }
                } else if (getBounds().intersects(e.getBounds())){
                    if (state==PlayerState.BIG) {
                        state = PlayerState.SMALL;
                        width/=3;
                        height/=3;
                        x+=width;
                        y+=height;
                    }
                    else if (state==PlayerState.SMALL){
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

        if (jumping&&!goingDownPipe) {
            gravity -= 0.17;
            setVelY((int) -gravity);
            if (gravity <= 0.5) {
                jumping = false;
                falling = true;
            }
        }

        if (falling&&!goingDownPipe) {
            gravity += 0.17;
            setVelY((int) gravity);
        }
        //oyuncu çerçevesi ile ilgili kodlar grafikler eklendiğinde düzeltilecektir.
        if (velX!=0){
            frameDelay++;
            if (frameDelay>=10) {//3 olabilir
                frame++;
                if (frame>3){//=5 olabilir
                    frame=0;
                }
                frameDelay=0;
            }

        }

        if (goingDownPipe) {
            for (int i=0;i<Game.handler.tile.size();i++) {
                Tile t = Game.handler.tile.get(i);
                if (t.getId()==Id.pipe) {

                    if (getBounds().intersects(t.getBounds())) {
                        switch (t.facing) {
                            case 0:
                                setVelY(-5);
                                setVelX(0);
                                pixelsTravelled+=-velY;
                                break;
                            case 2:
                                setVelY(5);
                                setVelX(0);
                                pixelsTravelled+=velY;
                                break;
                        }
                        if (pixelsTravelled>t.height) {
                            goingDownPipe = false;
                            pixelsTravelled = 0;
                        }
                    }
                }
            }
        }
    }
}
