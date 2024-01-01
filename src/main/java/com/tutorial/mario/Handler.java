package com.tutorial.mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.tutorial.mario.entity.Coin;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.mob.Goomba;
import com.tutorial.mario.entity.mob.Player;
import com.tutorial.mario.entity.mob.TowerBoss;
import com.tutorial.mario.entity.powerup.Mushroom;
import com.tutorial.mario.tile.*;

// Entiteleri kontrol etme sınıfı
public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public Handler() {
      //  createLevel();
    }

    // Grafikleri render etme metodu
    public void render(Graphics g) {
        // LinkedList<Entity> içindeki her bir Entity için 'en' adında bir Entity oluşturulur ve render metodu çağrılır.
        for  (Entity en:entity) {
            en.render(g);
        }

        // LinkedList<Tile> içindeki her bir Tile için 'ti' adında bir Tile oluşturulur ve render metodu çağrılır.
        for (Tile ti:tile) {
            ti.render(g);
        }
    }

    // Entity'leri güncelleme metodu
    public void tick() {
        // LinkedList<Entity> içindeki her bir Entity için 'en' adında bir Entity oluşturulur ve tick metodu çağrılır.
        for  (Entity en:entity) {
            en.tick();
        }

        // LinkedList<Tile> içindeki her bir Tile için 'ti' adında bir Tile oluşturulur ve tick metodu çağrılır.
        for  (Tile ti:tile) {
            ti.tick();
        }
    }

    // LinkedList<Entity>'e Entity ekleme metodu
    public void addEntity(Entity en) {
        entity.add(en);
    }

    // LinkedList<Entity>'den Entity kaldırma metodu
    public void removeEntity (Entity en) {
        entity.remove(en);
    }

    // LinkedList<Tile>'a Tile ekleme metodu
    public void addTile (Tile ti) {
        tile.add(ti);
    }

    // LinkedList<Tile>'dan Tile kaldırma metodu
    public void removeTile (Tile ti) {
        tile.remove(ti);
    }

    // Seviye oluşturma metodu
    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();

        for (int y=0;y<height;y++){
            for (int x=0;x<width;x++){
                int pixel = level.getRGB(x,y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red==0&&green==0&&blue==0) addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this));
                if (red==0&&green==0&&blue==255) addEntity(new Player(x*64,y*64,48,48,Id.player,this));
                if (red==255&&green==119&&blue==0) addEntity(new Goomba(x*64,y*64,64,64,Id.goomba,this));
                if (red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.lifeMushroom,1));//blue nın yanında sade 1 tane & işareti koyulabilir
                if (red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*64,y*64,64,64*15,true,Id.pipe,this,128-green));
                if (red==255&&green==255&&blue==0) addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
                if (red==255&&green==0&&blue==255) addEntity(new TowerBoss(x*64,y*64,64,64,Id.towerBoss,this,3));//hp değerini tekrar gözden geçir
            }
        }
    }
    public void clearLevel() {
        entity.clear();
        tile.clear();
    }

}
