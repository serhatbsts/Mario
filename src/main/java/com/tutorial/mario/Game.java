package com.tutorial.mario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import com.tutorial.mario.entity.Player;
import com.tutorial.mario.input.KeyInput;
import com.tutorial.mario.tile.Wall;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 250;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static String TITLE = "Mario";
    private Thread thread;
    private boolean running = false;
    public static Handler handler;

    // Main metodu, ana pencereyi oluşturur
    public static void main(String[] args) {
        Game game = new Game(); // Game tipinde bir nesne oluşturur.
        JFrame frame = new JFrame(TITLE); // JFrame tipinde bir nesne oluşturur ve pencere başlığını ayarlar.
        frame.add(game); // Game tipindeki nesneyi frame'e ekler.
        frame.pack(); // Pack metodu, içindeki tüm bileşenlerin tercih edilen boyutlarında veya üzerinde olacak şekilde pencereyi boyutlandırır.
        frame.setResizable(false); // Pencerenin yeniden boyutlandırılmasını engeller.
        frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına konumlandırır.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); // Pencereyi görünür hale getirir.

        game.start();
    }

    // Genişlik, yükseklik ve ölçekleme parametrelerini belirleyen yapılandırıcı
    public Game() {
        Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();

        addKeyListener(new KeyInput());

        // Ekran üzerine bir oyuncu ekler
        handler.addEntity(new Player(300, 512, 64, 64, true, Id.player, handler));
    }

    // Oyunu başlatmak için kullanılan metot
    private synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
    }

    // Oyunu durdurmak için kullanılan metot
    private synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join(); // Metot, belirtilen thread ölünceye kadar geçerli thread'in çalışmasını durdurmak için kullanılır.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Oyunun çalışma döngüsünü içeren metot
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0; // 1 nanosaniye
        int frames = 0;
        int ticks = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " Frames Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    // Ekran üzerine çizim yapmak için kullanılan metot
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0 , getWidth(), getHeight());
        handler.render(g);
        g.dispose();
        bs.show();
    }

    // Oyunun güncellenmesi için kullanılan metot
    public void tick() {
        handler.tick();
    }
}
