package com.tutorial.mario;

import javax.swing.*;
import java.awt.*;

public class Game extends Canvas{
    public static final int WIDTH=270;
    public static final int HEIGHT=WIDTH/14*20;
    public static final int SCALE=4;
    public static final String TITLE="Mario";

    public Game(){
        Dimension size=new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);


    }

    public static void main(String[] args) {
        Game game=new Game();
        JFrame frame=new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}