package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MajorHyperMatterBooster<T extends GameObjects> extends CollectibleObject {
    private BufferedImage MB;
    private T object;

    public MajorHyperMatterBooster (T object) {
        super();
        this.object = object;

        posX = object.getPosX();
        posY = object.getPosY();

//        MB = setImage();
        hitbox = new Circle(Operations.centerRect(posX, posX + 16), Operations.centerRect(posY, posY + 16), 8);
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {

//            gfx.drawImage(MB, posX, posY, null);

            gfx.setColor(Color.MAGENTA);
            gfx.drawRect(posX, posY, 16, 16);
        }
    }
}
