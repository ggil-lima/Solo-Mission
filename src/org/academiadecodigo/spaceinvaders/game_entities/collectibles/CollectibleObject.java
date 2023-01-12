package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

public abstract class CollectibleObject extends GameObjects implements Collectibles{

    protected boolean isHit;
    protected Circle hitbox;
    private int animationCounter;
    private int animationFrame;


    public CollectibleObject() {
        animationCounter = 0;
        isHit = false;
        animationFrame = 1;
    }

    @Override
    public void animation() {
        if (animationCounter <= 30) {
            if (animationCounter % 6 == 0) {
                animationFrame++;
            }
        }
        if (animationCounter > 30 && animationCounter <= 60) {
            if (animationCounter % 6 == 0) {
                animationFrame--;
            }
        }
        if (animationCounter > 90 && animationCounter <= 120 ) {
            if (animationCounter % 6 == 0) {
                animationCounter++;
            }
        }
        if (animationCounter > 120 && animationCounter <= 180) {
            if (animationCounter % 6 == 0) {
                animationFrame--;
            }
        }
        if (animationCounter > 210 && animationCounter <= 240) {
            if (animationCounter % 6 == 0) {
                animationFrame++;
            }
        }
        if (animationCounter > 240 && animationCounter <= 270) {
            if (animationCounter % 6 == 0) {
                animationFrame--;
            }
        }
    }

    @Override
    public void behaviour() {
        if(!isDestroyed) {
            animationCounter++;

            if (isHit || animationCounter > 300) {
                isDestroyed = true;
            }
        }
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
