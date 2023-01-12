package org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star;

import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperLaser extends GameObjects {
    private int posY;
    private int posX;
    private DeathStar dS;
    private Rectangle beam;
    private int sLaserCounter;
    private int animationCounter;
    private boolean ignite;

    private BufferedImage supaLaser;

    public SuperLaser(DeathStar dS) {
        this.dS = dS;
        posX = dS.getPosX();
        posY = dS.getPosY();

        sLaserCounter = 0;

        ignite = false;
        isDestroyed = false;
    }
    public void sLaserCharge() {
        sLaserCounter++;
         if(sLaserCounter > 1200 ) {
             ignite = true;
             sLaserCounter = 0;
         }
    }

    private void animationLogic() {
        if (sLaserCounter % 2 == 0) {
            animationCounter++;
        }

        if(animationCounter > 8) {
            animationCounter = 8;
        }
    }

    private String sLaserAnimation() {
        if(!ignite) {
            return "/SuperLaser1.png";
        } else {
            return "/SuperLaser" + animationCounter + ".png";
        }
    }

    public void draw(Graphics2D gfx) {
        if(ignite) {
            animationLogic();
        }
        supaLaser = setImage(sLaserAnimation());

        gfx.drawImage(supaLaser, dS.getPosX(), dS.getPosY(), null );
    }

    public boolean isIgnite() {
        return ignite;
    }
}
