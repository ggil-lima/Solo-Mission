package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MinorHyperMatterBooster<T extends GameObjects> extends CollectibleObject {
    private BufferedImage mB;
    private T object;

    public MinorHyperMatterBooster (T object) {
        super();
        this.object = object;

        posX = object.getPosX();
        posY = object.getPosY();

        hitbox = new Circle(Operations.centerRect(posX, posX + 16), Operations.centerRect(posY, posY + 16), 8);
//        hc = setImage();
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {

//            gfx.drawImage(mB, posX, posY, null);

            gfx.setColor(Color.ORANGE);
            gfx.drawRect(posX, posY, 16, 16);
        }
    }
}
