package com.tutorial.mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;

public abstract class Entity {
    // Değişkenlerin tanımlanması
    public int x, y;
    public int width, height;
    public int facing=0;//genel arayüz  0-sol ,1- sağ
    public int velX, velY;
    public double gravity = 0.0;
    public boolean solid;
    public boolean jumping = false;
    public boolean falling = true;
    public Id id;
    public Handler handler;

    // Yapıcı metot
    public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
    }

    // Getter ve Setter metotları
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

    public Id getId() {
        return id;
    }

    // Çarpışma için
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(getX() + 10, getY(), width - 20, 5);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(getX() + 10, getY() + height - 5, width - 20, 5);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(getX(), getY() + 10, 5, height - 20);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(getX() + width - 5, getY() + 10, 5, height - 20);
    }

    // Soyut metotlar
    public abstract void render(Graphics g);

    public abstract void tick();

    // Ölme metodu
    public void die() {
        handler.removeEntity(this); // this, çünkü tüm sınıfa referans verir
    }
}
