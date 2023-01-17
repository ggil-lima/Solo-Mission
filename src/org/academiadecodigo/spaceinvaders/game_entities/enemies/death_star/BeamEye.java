package org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star;


import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BeamEye extends GameObjects {
    private Circle hitbox;
    private BufferedImage eye;
    private int animationCounter;

    public BeamEye(){
        posX =  1265;
        posY = 235;
        maxPosX = posX + 16;
        maxPosY = posY + 16;
        isDestroyed = false;
        lives = 50;
        hitbox = new Circle(Operations.centerRect(posX, maxPosX), Operations.centerRect(posY, maxPosY), 12);
    }
    public void reduceLives(){
        lives--;
    }
    public int getLives(){
        return lives;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    private void animationLogic() {
        animationCounter++;

        if(animationCounter > 11) {
            animationCounter = 1;
        }
    }

    public String eyeAnimation() {
        return  "/eye" + animationCounter + ".png";
    }

    public void draw(Graphics2D gfx){
        animationLogic();

        eye = setImage(eyeAnimation());

        gfx.drawImage(eye, posX, posY, null);
    }


}
