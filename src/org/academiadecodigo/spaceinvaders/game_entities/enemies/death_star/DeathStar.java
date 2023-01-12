package org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star;


import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DeathStar extends GameObjects {

    private BufferedImage dS;
    private GameBrain window;


    public DeathStar(GameBrain window){
        super();
        this.window = window;
        setPosX(window.getWidth());
        //setPosX(1121);
        setPosY(120);
        isDestroyed = false;
        dS = setImage("/DeathStar10.png");
    }

    public void draw(Graphics2D gfx){
        gfx.drawImage(dS, posX, posY, null);
    }

    public void moveDS() {

        if(posX > 1121) {
            posX -= speed;
        }
    }
}
