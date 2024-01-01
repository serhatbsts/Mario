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

    public static final int WIDTH = 250;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static String TITLE = "Mario";
    private Thread thread;
    private boolean running = false;
    private BufferedImage image;
    public static int coins = 0;
    public static int lives = 5;
    public static int deathScreenTime = 0;
    public static boolean showDeatScreen = true; //showDeathScreen olarak değiştirebilirsin
    public static boolean gameOver = false;
    public static boolean playing =false;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MauseInput mause;
    public static Sprite grass;//çim grafiği
    public static Sprite powerUp;
    public static Sprite usedPowerUp;

    public static Sprite mushroom;//mantar grafiği
    public static Sprite lifeMushroom;
    public static Sprite coin;
    public static Sprite player[];//oyuncu grafiği

    public static Sprite[] goomba;


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
        sheet = new SpriteSheet("/spritesheet.png");//resim dosyalarını eklemek için yapıldı
        cam = new Camera();
        launcher = new Launcher();
        mause = new MauseInput();

        addKeyListener(new KeyInput());
        addMouseListener(mause);
        addMouseMotionListener(mause);//Mouse veya Mause olabilir tekrar bak

        grass = new Sprite(sheet,2,1);//çim ile ilgili kordinatlar
        powerUp = new Sprite(sheet,3,1);
        usedPowerUp = new Sprite(sheet,4,1);
      //  player = new Sprite(sheet,2,2);//oyuncu ile ilgili kordinatlar  dizi olarak tanımlanmaz ise
        mushroom = new Sprite(sheet,1,1);
        lifeMushroom = new Sprite(sheet,6,1);
        coin = new Sprite(sheet,5,1);
        player = new Sprite[8];
        goomba=new Sprite[8];

        //oyuncu ile ilgili kordinatlar  dizi olarak tanımlanır ise
        for (int i=0;i< player.length; i++){
            player[i]=new Sprite(sheet,i+1,16);
        }
        //canavar ile ilgili kordinatlar dizi olarak tanımlanır ise
        for (int i=0;i< goomba.length; i++){
            goomba[i]=new Sprite(sheet,i+1,15);
        }

        // Ekran üzerine bir oyuncu ekler
       // handler.addEntity(new Player(300, 512, 64, 64, true, Id.player, handler));

        //levellerin olduğu yere atanır grafikler eklendiğinde sorun kalmayacaktır
        try {
            image= ImageIO.read(getClass().getResource("/level.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

       // handler.createLevel(image);
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
        if (!showDeatScreen){
            g.drawImage(coin.getBufferedImage(), 20,20,75,75,null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier",Font.BOLD,20));
            g.drawString("x"+coins,100,95);

        }
        if (showDeatScreen) {
            if (!gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier",Font.BOLD,50));
                g.drawImage(Game.player[0].getBufferedImage(), 500,300,100,100,null);
                g.drawString("x"+lives,300,400);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier",Font.BOLD,50));
                g.drawString("GAME OVER :(",610,400);

            }

        }

        if(playing) g.translate(cam.getX(),cam.getY());
        if(!showDeatScreen&&playing) handler.render(g);
        else if (!playing) launcher.render(g);
        g.dispose();
        bs.show();
    }

    public static int getFrameWidht(){
        return WIDTH*SCALE;
    }

    public static int getFrameHeight(){
        return HEIGHT*SCALE;
    }

    // Oyunun güncellenmesi için kullanılan metot
    public void tick() {
       if(playing) handler.tick();

        for (Entity e: handler.entity){
            if (e.getId()==Id.player){
               if (!e.goingDownPipe) cam.tick(e);
            }
        }

        if (showDeatScreen&&!gameOver&&playing) deathScreenTime++;
        if (deathScreenTime>=180) {
            if (!gameOver) {
                showDeatScreen = false;
                deathScreenTime = 0;
                handler.clearLevel();
                handler.createLevel(image);
            }else if (gameOver) {
                showDeatScreen = false;
                deathScreenTime = 0;
                playing = false;
                gameOver = false;
            }

        }
    }


}
