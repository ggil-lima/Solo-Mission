package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HyperCoolant<T extends GameObjects> extends CollectibleObject{

    private BufferedImage hc;
    private T object;

    public HyperCoolant (T object) {
        super();
        this.object = object;

        posX = object.getPosX();
        posY = object.getPosY();

//        hc = setImage();
        hitbox = new Circle(Operations.centerRect(posX, posX + 16), Operations.centerRect(posY, posY + 16), 8);
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {
//            gfx.drawImage(hc, posX, posY, null);

            gfx.setColor(Color.PINK);
            gfx.drawRect(posX, posY, 16, 16);
        }
    }

}
