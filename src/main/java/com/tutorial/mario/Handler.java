package com.tutorial.mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.tutorial.mario.entity.Coin;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.mob.Goomba;
import com.tutorial.mario.entity.mob.Player;
import com.tutorial.mario.tile.*;

// Entiteleri kontrol etme sınıfı
public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public Handler() {
      // createLevel();
    }

    // Grafikleri render etme metodu
    public void render(Graphics g) {
        for  (Entity en:entity) {
            if (Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())) en.render(g);
        }


        for (Tile ti:tile) {
            if (Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea())) ti.render(g);
        }
        for  (Entity en:entity) {
           if (Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())) en.render(g);
        }




    }

    // Entity'leri güncelleme metodu
    public void tick() {
        for  (Entity en:entity) {
         if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea()))  en.tick();

        }

        for  (Tile ti:tile) {
            if (Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea())) ti.tick();
        }

    }

    public void addEntity(Entity en) {
        entity.add(en);
    }

    public void removeEntity (Entity en) {
        entity.remove(en);
    }

    public void addTile (Tile ti) {
        tile.add(ti);
    }

    public void removeTile (Tile ti) {
        tile.remove(ti);
    }

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
                if (red==255&&green==255&&blue==0) addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
            }
        }
    }
    public void clearLevel() {
        entity.clear();
        tile.clear();
    }

}
