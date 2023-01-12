package org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;


import java.awt.*;
import java.awt.image.BufferedImage;


public class EnemyFighter extends GameObjects {

    private BufferedImage eFighter;
    private GameBrain window;
    private boolean isOutside;
    private Rectangle hitbox;


    public EnemyFighter(GameBrain window){
        super();
        this.window = window;
        posX = 1360;
        posY = (int) (Math.random() * (700 - 53) + 52);
        isOutside = false;
        isEnemy = true;
        eFighter = setImage("/alien.png");
        speed = 2;

        hitbox = new Rectangle(posX + 20, posY + 12, 50, 37);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public synchronized void moveFighter() {
        if (posX <= -55) {
            isOutside = true;
        }
        posX -= speed;
        maxPosX -=speed;
        hitbox.setX(posX + 15);
    }
    public synchronized void summonFighter(Graphics2D gfx){
        if(!isDestroyed || !isOutside) {
            gfx.drawImage(eFighter, posX, posY, window.getCellSize(), window.getCellSize(), null);
        }
    }
    public boolean isOutside() {
        return isOutside;
    }
}
