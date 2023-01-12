package org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.Vader;

import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.game_entities.Weapons;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;

public class VaderLasers extends GameObjects implements Weapons {
    private int posY2;
    private int maxPosY2;
    private GameBrain window;

    private Rectangle hitbox;

    private boolean deflected;


    public VaderLasers(int posY, int posX, GameBrain window) {
        super();
        this.window = window;
        setPosX(posX);
        setPosY(posY);
        posY2 = posY + 74;
        maxPosY2 = posY2 + 4;
        speed = 10;
        deflected = false;

        hitbox = new Rectangle(posX, posY, 16, 4);


    }

    @Override
    public void move() {
        if(deflected) {
            if (posX >= 1360) {
                isDestroyed = true;
            }
            posX += speed;
            maxPosX += speed;
            hitbox.setX(posX);
        }
        if(!deflected) {
            if (posX < -55) {
                isDestroyed = true;
            }
            posX -= speed;
            maxPosX -= speed;

            hitbox.setX(posX);
        }
    }

    @Override
    public void draw(Graphics2D gfx) {

        if (!isDestroyed) {
            gfx.setColor(new Color(10, 250, 100));
            gfx.fillRect(posX, posY, 16, 4);
        }

    }

    public int getMaxPosX() {
        return posX + window.getCellSize();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getMaxPosY() {
        return posY + window.getCellSize() / 4;
    }

    public int getPosY2() {
        return posY2;
    }

    public int getMaxPosY2() {
        return maxPosY2;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public boolean isDeflected() {
        return deflected;
    }
    public void deflected() {
        deflected = true;
    }

    public void reDeflected() { deflected = false; }
}
