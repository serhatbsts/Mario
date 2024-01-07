package com.tutorial.mario;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.gfx.Sprite;
import com.tutorial.mario.gfx.SpriteSheet;
import com.tutorial.mario.gfx.gui.Launcher;
import com.tutorial.mario.input.KeyInput;
import com.tutorial.mario.input.MauseInput;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 720;
    public static final int HEIGHT = WIDTH / 14 * 10;
    public static final int SCALE = 4;
    public static String TITLE = "Mario";
    private Thread thread;
    private boolean running = false;
    private static BufferedImage[] levels;
    private static BufferedImage backGround;
    private static int playerX, playerY;
    private static int level = 1;//ikinci haritayı çalıştır
    public static int coins = 0;
    public static int lives = 5;
    public static int deathScreenTime = 0;
    public static boolean showDeatScreen = true; //showDeathScreen olarak değiştirebilirsin
    public static boolean gameOver = false;
    public static boolean playing = false;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MauseInput mause;
    public static Sprite grass;
    public static Sprite coin;
    public static Sprite door;

    //  public static Sprite fireBall;
    // public static Sprite flower;
    public static Sprite[] player;//oyuncu grafiği

    public static Sprite[] goomba;
    public static Sprite[] flag;
    public static Sprite[] particle;


    // Main metodu, ana pencereyi oluşturur
    public static void main(String[] args) {
        System.out.println(getFrameHeight());
        System.out.println(getFrameWidht());
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
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/spritesheet.png");//resim dosyalarını eklemek için yapıldı
        cam = new Camera();
        launcher = new Launcher();
        mause = new MauseInput();


        addKeyListener(new KeyInput());
        addMouseListener(mause);
        addMouseMotionListener(mause);//Mouse veya Mause olabilir tekrar bak

        grass = new Sprite(sheet, 1, 1);//çim ile ilgili kordinatlar
        coin = new Sprite(sheet, 5, 1);
        door = new Sprite(sheet,3,1);
        player = new Sprite[8];
        goomba = new Sprite[8];
        flag = new Sprite[3];
        particle = new Sprite[6];
        levels = new BufferedImage[2];

        //oyuncu ile ilgili kordinatlar  dizi olarak tanımlanır ise
        for (int i = 0; i < player.length; i++) {
            player[i] = new Sprite(sheet, i + 1, 16);
        }
        //canavar ile ilgili kordinatlar dizi olarak tanımlanır ise
        for (int i = 0; i < goomba.length; i++) {
            goomba[i] = new Sprite(sheet, i + 1, 15);
        }
        for (int i = 0; i < particle.length; i++) {
            particle[i] = new Sprite(sheet, i + 1, 14);
        }
        try {
            levels[0] = ImageIO.read(getClass().getResource("/level.png"));
            levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " Frames Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }
            render();
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


        g.setColor(Color.BLACK);//tekrar bak
        g.fillRect(0, 0, getWidth(), getHeight());//tekrar bak
        if (!showDeatScreen) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(Game.coin.getBufferedImage(), 20, 20, 75, 75,null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("x" + Game.coins, 100, 95);
            ///tekrar bak buraya

        }
        if (showDeatScreen) {
            if (!gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 50));
                g.drawImage(Game.player[0].getBufferedImage(), 500, 300, 100, 100, null);
                g.drawString("x" + lives, 300, 400);
            } else {
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 50));
                g.drawString("Total Coin :"+coins, 610, 400);

            }

        }

        if (playing) g.translate(cam.getX(), cam.getY());
        if (!showDeatScreen && playing) handler.render(g);
        else if (!playing) launcher.render(g);
        g.dispose();
        bs.show();
    }

    public static int getFrameWidht() {
        return WIDTH * SCALE;
    }

    public static int getFrameHeight() {
        return HEIGHT * SCALE;
    }

    public static void switchLeve() {
        Game.level++;

        handler.clearLevel();
        handler.createLevel(levels[level]);


    }

    public static Rectangle getVisibleArea() {
        for (Entity e : handler.entity) {
            if (e.getId() == Id.player) {
                    playerX = e.getX();
                    playerY = e.getY();
                    return new Rectangle(playerX - (getFrameWidht() / 2 - 5), playerY - (getFrameHeight() / 2 - 5), getFrameWidht() + 10, getFrameHeight() + 10);
            }
        }
        return new Rectangle(playerX - (getFrameWidht() / 2 - 5), playerY - (getFrameHeight() / 2 - 5), getFrameWidht() + 10, getFrameHeight() + 10);
    }

    // Oyunun güncellenmesi için kullanılan metot
    public void tick() {
        if (playing) handler.tick();

        for (Entity e: handler.entity){
            if (e.getId()==Id.player){
                cam.tick(e);
            }
        }



        if (showDeatScreen && !gameOver && playing) deathScreenTime++;
        if (deathScreenTime >= 180) {
            if (!gameOver) {
                showDeatScreen = false;
                deathScreenTime = 0;
                handler.clearLevel();
                handler.createLevel(levels[level]);


            } else if (gameOver) {
                showDeatScreen = false;
                deathScreenTime = 0;
                playing = false;
                gameOver = false;
            }

        }
    }
}


