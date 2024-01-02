package com.tutorial.mario.tile;

import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.mob.Plant;

import java.awt.*;

public class Pipe extends Tile{
    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) {
        super(x, y, width, height, solid, id, handler);
        this.facing=facing;

        if (plant) handler.addEntity(new Plant(getX(),getY()-64,getWidth(),64,Id.plant,handler));//-64 olmayabilir
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(128,128,128));
        g.fillRect(x,y,width,height);

    }

    @Override
    public void tick() {

    }
}
