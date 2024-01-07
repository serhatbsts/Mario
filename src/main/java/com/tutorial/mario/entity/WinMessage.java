package com.tutorial.mario.entity;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;

import java.awt.*;

public class WinMessage extends Entity{
    public WinMessage(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD,36));

        String message="**************KAZANDINIZ***********";
        int messageWidht = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (Game.WIDTH-messageWidht)/2, Game.HEIGHT/2);
    }

    @Override
    public void tick() {

    }
}
