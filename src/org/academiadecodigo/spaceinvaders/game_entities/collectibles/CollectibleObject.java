package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;

public abstract class CollectibleObject extends GameObjects implements Collectibles{

    protected boolean isHit;
    protected boolean flicker;
    protected Circle hitbox;
    private int animationCounter;



    public CollectibleObject() {
        animationCounter = 0;
        isHit = false;
        flicker = false;
    }

    @Override
    public void animation(Graphics2D gfx) {
        if(animationCounter >= 300 && animationCounter < 360) {
            if(animationCounter % 10 == 0) {
                fading();
            }
        }
        if(animationCounter >= 360 && animationCounter < 420) {
            if(animationCounter % 5 == 0) {
                fading();
            }
        }
        if(animationCounter >= 420 && animationCounter < 480) {
            if(animationCounter % 2 == 0) {
                fading();
            }
        }
    }

    @Override
    public void behaviour() {
        if(!isDestroyed) {
            animationCounter++;

            if (isHit || animationCounter > 480) {
                isDestroyed = true;
            }
        }
    }

    @Override
    public void fading() {
        if(!flicker) {
            flicker = true;
            return;
        }
        flicker = false;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
