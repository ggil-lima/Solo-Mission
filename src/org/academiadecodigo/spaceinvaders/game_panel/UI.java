package org.academiadecodigo.spaceinvaders.game_panel;

import org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon.Spaceship;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class UI {

    private GameBrain window;
    private Spaceship sShip;

    private int score;
    private BufferedImage pause;
    private BufferedImage torpedo;






    public UI(GameBrain window, Spaceship sShip) {
        this.window = window;
        this.sShip = sShip;
        score = 0;

        try {
            pause = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pause.png")));
            torpedo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/MFPT.png")));
        } catch (IOException e) {
            e.printStackTrace();        }
//
    }

    public synchronized void hpBar(Graphics2D gfx){
        double barScale = 320/sShip.getMaxLives();
        double currentHP = barScale * sShip.getLives();

        gfx.setColor(new Color(0,0,255));
        gfx.fillRect(10, 10, 322, 22);

        gfx.setColor(new Color(240,240,240));
        gfx.fillRect(12, 12, 320, 20);

        gfx.setColor(new Color(10,150,255));
        gfx.fillRect(12, 12, (int) currentHP, 20);
    }

    public synchronized void overheatBar(Graphics2D gfx){
        double barScale = 320/sShip.getCannonOverheat();
        double currentHeat = barScale * sShip.getCannonHeat();


        gfx.setColor(new Color(65, 3, 3));
        gfx.fillRect(350, 10, 322, 22);

        gfx.setColor(new Color(255, 237, 164));
        gfx.fillRect(352, 12, 320, 20);

        switch (sShip.getCannonHeat()) {
            case 1 -> {gfx.setColor(new Color(201, 185, 40));
                gfx.fillRect(352, 12, (int) currentHeat, 20);}
            case 2 -> {gfx.setColor(new Color(201, 145, 40));
                gfx.fillRect(352, 12, (int) currentHeat, 20);}
            case 3 -> {gfx.setColor(new Color(201, 96, 40));
                gfx.fillRect(352, 12, (int) currentHeat, 20);}
            case 4 -> {gfx.setColor(new Color(201, 59, 40));
                gfx.fillRect(352, 12, (int) currentHeat, 20);}
            case 5 -> {gfx.setColor(new Color(126, 18, 18));
                gfx.fillRect(352, 12, (int) currentHeat, 20);}

        }

    }

    public synchronized void drawTorpedoUI(Graphics2D gfx) {
        gfx.drawImage(torpedo, 700, 11, null);

        switch (sShip.getTorpedoCharges()) {
            case 0 -> {
                gfx.setColor(new Color(120, 120, 120));
                gfx.fillRect(740, 12, 10, 20);
                gfx.fillRect(760, 12, 10, 20);
                gfx.fillRect(780, 12, 10, 20);
            }
            case 1 -> {
                gfx.setColor(new Color(201, 59, 40));
                gfx.fillRect(740, 12, 10, 20);
                gfx.setColor(new Color(120, 120, 120));
                gfx.fillRect(760, 12, 10, 20);
                gfx.fillRect(780, 12, 10, 20);
            }
            case 2 -> {
                gfx.setColor(new Color(201, 59, 40));
                gfx.fillRect(740, 12, 10, 20);
                gfx.fillRect(760, 12, 10, 20);
                gfx.setColor(new Color(120, 120, 120));
                gfx.fillRect(780, 12, 10, 20);
            }
            case 3 -> {
                gfx.setColor(new Color(201, 59, 40));
                gfx.fillRect(740, 12, 10, 20);
                gfx.fillRect(760, 12, 10, 20);
                gfx.fillRect(780, 12, 10, 20);
            }
        }
    }


    public synchronized void drawScore(Graphics2D gfx){
        gfx.setFont(gfx.getFont().deriveFont(Font.PLAIN, 35f));
        String scoreShadow = "SCORE: " + score;

        gfx.setFont(gfx.getFont().deriveFont(Font.PLAIN, 30f));
        String scoreText = "SCORE: " + score;

        gfx.setColor(new Color(100, 100, 100));
        gfx.drawString(scoreShadow,1130, 30);

        gfx.setColor(new Color(255, 255, 255));
        gfx.drawString(scoreText,1132, 32);

    }



    public synchronized void addScore() {
        score++;
    }
    public synchronized  void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public synchronized void drawPauseScreen(Graphics2D gfx) {
        gfx.drawImage(pause,window.getWidth()/2 - 321, window.getHeight()/2 - 66, null);
    }

}
