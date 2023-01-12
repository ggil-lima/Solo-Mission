package org.academiadecodigo.spaceinvaders.game_entities.ship_features;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DeflectorShield<T extends GameObjects> extends GameObjects {

    private T object;
    private int shieldCounter;
    private final int maxLives = 5;
    private int currentLives;

    private Circle hitbox;
    private BufferedImage shield;


    public DeflectorShield (T object) {
        this.object = object;
        lives = maxLives;
        currentLives = lives;
        posX = object.getPosX();
        posY = object.getPosY();

        if(isEnemy) {
            hitbox = new Circle(Operations.centerRect(posX, posX + 62), Operations.centerRect(posY, posY + 87), 76);
        } else {
            hitbox = new Circle(Operations.centerRect(posX, posX + 116), Operations.centerRect(posY, posY + 96), 76);
        }
    }

    public void surround() {

        if(object.isEnemy()) {
            posX = (object.getPosX() - 43);
            posY = (object.getPosY() - 34);
            maxPosX = posX + 153;
            maxPosY = posY + 153;
            hitbox.setCenterX(Operations.centerRect(object.getPosX(), object.getPosX() + 62));
            hitbox.setCenterY(Operations.centerRect(object.getPosY(), object.getPosY() + 87));
        } else {
            posX = (object.getPosX() - 26);
            posY = (object.getPosY() - 31);
            maxPosX = posX + 153;
            maxPosY = posY + 153;
            hitbox.setCenterX(Operations.centerRect(object.getPosX(), object.getPosX() + 116));
            hitbox.setCenterY(Operations.centerRect(object.getPosY(), object.getPosY() + 96));
        }

        if(lives <= 0 || object.isDestroyed()) {
            isDestroyed = true;
        }
    }


    public void regen () {


        if(!isDestroyed) {
            if (lives == maxLives) {
                shieldCounter = 0;
            }
            if (lives < maxLives) {
                shieldCounter++;
                if (shieldCounter > 30) {
                    if (shieldCounter % 30 == 0) {
                        lives++;
                    }
                }
            }
        }
    }

    private String animation() {
        return switch (lives) {
            case 5 -> "/shield5.png";
            case 4 -> "/shield4.png";
            case 3 -> "/shield3.png";
            case 2 -> "/shield2.png";
            case 1 -> "/shield1.png";
            default -> "";
        };
    }

    public void resetLives() {
        lives = maxLives;
    }

    public void draw (Graphics2D gfx) {

        if(!isDestroyed()) {
            shield = setImage(animation());
            gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            gfx.drawImage(shield, posX, posY, null);
            gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public Circle getHitbox() {
        return hitbox;
    }
}
