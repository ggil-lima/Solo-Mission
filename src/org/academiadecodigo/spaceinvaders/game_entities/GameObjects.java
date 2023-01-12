package org.academiadecodigo.spaceinvaders.game_entities;

import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class GameObjects {

    protected int speed;
    protected int lives;
    protected int posX;
    protected int posY;
    protected int maxPosX;
    protected int maxPosY;
    protected  BufferedImage image;

    protected boolean isDestroyed;
    protected boolean isEnemy;


    public GameObjects() {
        speed = 1;
        lives = 1;
        isDestroyed = false;
        isEnemy = false;
    }

    public BufferedImage setImage(String path) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }



    public int getLives() {
        return lives;
    }

    public void reduceLives() {
        lives--;
    }
    public void OHKO() { lives = 0; }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX(){
        return posX;
    }

    public int getMaxPosX(){
        return maxPosX;
    }

    public void setMaxPosX(int maxPosX) {
        this.maxPosX = maxPosX;
    }

    public int getPosY(){
        return posY;
    }

    public int getMaxPosY(){
        return maxPosY;
    }

    public void setMaxPosY(int maxPosY) {
        this.maxPosY = maxPosY;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean isDestroyed){
        this.isDestroyed = isDestroyed;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
