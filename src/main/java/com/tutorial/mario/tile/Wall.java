package com.tutorial.mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;

public class Wall extends Tile{

    public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        // TODO: Otomatik oluşturulan yapıcı metot
    }

    // Grafik çizme metodu
    public void render(Graphics g) {

     // Duvarlarla ilgili özellikler burada geçici olarak verilmiştir
     // Grafikler eklenince devre dışı bırakılacaktır.

      // g.setColor(Color.BLUE);
      // g.fillRect(x, y, width, height);


        g.drawImage(Game.grass.getBufferedImage(), x,y,width,height,null);
    }

    // Güncelleme metodu
    public void tick() {

    }

}
