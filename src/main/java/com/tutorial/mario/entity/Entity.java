package com.tutorial.mario.entity;

import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;

import java.awt.*;

public abstract class Entity {
    public int x,y;
    public int width,height;

    public int velX,velY;
    public double gravtiy=0.0;//yer çekimi

    public boolean solid;

    public boolean jumping=false;//atlama
    public boolean falling=true;//yere düşme açık
    public Id id;
    public Handler handler;



    public Entity(int x, int y, int width, int height, boolean solid, Id id,Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id=id;
        this.handler=handler;
    }



    public void die(){
        handler.removeEntity(this);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }


    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Id getId(){
        return id;
    }


    public Rectangle getBounds(){//dikdörtgenler için sınır oluşturmal için yazılmıştır.

        return new Rectangle(getX(),getY(),width,height);
    }
    public Rectangle getBoundsTop(){//engeller için üstten sınırları belirler
        return new Rectangle(getX()+10,getY(),width-20,5);
    }
    public Rectangle getBoundsBottom(){//engeller için alttan sınırları belirler
        return new Rectangle(getX()+10,getY()+height-5,width-20,5);
    }
    public Rectangle getBoundsLeft(){//engeller için soldan sınırları belirler
        return new Rectangle(getX(),getY()+10,5,height-20);
    }
    public Rectangle getBoundsRight(){//engeller için soldan sınırları belirler
        return new Rectangle(getX()+width-5,getY()+10,5,height-20);
    }



    public abstract void render(Graphics g);

    public abstract void tick();

}
