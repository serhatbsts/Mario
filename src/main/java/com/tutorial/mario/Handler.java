package com.tutorial.mario;

import java.awt.Graphics;
import java.util.LinkedList;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;
import com.tutorial.mario.tile.Wall;

// Entiteleri kontrol etme sınıfı
public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public Handler() {
        createLevel();
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
    public void createLevel() {
        for(int i=0; i<Game.WIDTH*Game.SCALE/64+1; i++){
            addTile(new Wall(i*64, Game.HEIGHT*Game.SCALE-64, 64, 64, true, Id.wall, this));
            if(i != 0 && i != 1 && i != 15 && i != 16 && i != 17) {
                addTile(new Wall(i*57, 300, 64, 64, true, Id.wall, this));
            }
        }
    }
}
