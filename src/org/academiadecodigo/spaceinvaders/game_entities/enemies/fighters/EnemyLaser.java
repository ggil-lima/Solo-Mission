package org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters;

import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.game_entities.Weapons;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;

public class EnemyLaser extends GameObjects implements Weapons {

    private GameBrain window;
    private Rectangle hitbox;
    private boolean deflected;


    public EnemyLaser(int posY, int posX, GameBrain window) {
        super();
        this.window = window;
        speed = 10;
        deflected = false;

        setPosX(posX + 27);
        setPosY(posY + 23);
        setMaxPosX(this.posX + 16);
        setMaxPosY(this.posY + 4);

        hitbox = new Rectangle(this.posX, this.posY, 16, 4);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

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

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {
            gfx.setColor(new Color(10, 250, 100));
            gfx.fillRect(posX, posY, 16, 4);
        }
    }

    public boolean isDeflected() {
        return deflected;
    }
    public void deflected() {
        deflected = true;
    }
}