package org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon;

import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.game_entities.Weapons;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;

public class MFLasers extends GameObjects implements Weapons {

    private int damage;
    private GameBrain window;
    private Rectangle hitbox;
    private boolean deflected;
    private boolean criticalShot;



    public Rectangle getHitbox() {
        return hitbox;
    }

    public MFLasers(int posY, int posX, boolean criticalShot, GameBrain window) {
        super();
        this.window = window;
        this.criticalShot = criticalShot;
        setPosX(posX + 68);
        setPosY(posY + 42);
        setMaxPosX(this.posX + 16);
        setMaxPosY(this.posY + 4);
        speed = 12;
        deflected = false;

        hitbox = new Rectangle(this.posX, this.posY, 16, 4);
    }
    public void move(){
        if(!deflected) {
            if (posX >= 1360) {
                isDestroyed = true;
            }
            posX += speed;
            maxPosX += speed;
            hitbox.setX(posX);

        }
        if(deflected) {
            if (posX < -55) {
                isDestroyed = true;
            }
            posX -= speed;
            maxPosX -= speed;
            hitbox.setX(posX);
        }

    }

    public boolean isDeflected() {
        return deflected;
    }
    public void deflected() {
        deflected = true;
    }
    public void reDeflected() {
        deflected = false;
    }

    public boolean isCriticalShot() {
        return criticalShot;
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {
            if (criticalShot) {
                gfx.setColor(new Color(43, 182, 234));
                gfx.fillRect(posX, posY, 16, 4);
            } else {
                gfx.setColor(new Color(255, 10, 10));
                gfx.fillRect(posX, posY, 16, 4);
            }
        }
    }



}
