package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShieldGenerator<T extends GameObjects> extends CollectibleObject {
    private BufferedImage sG;
    private T object;

    public ShieldGenerator (T object) {
        super();
        this.object = object;

        posX = object.getPosX();
        posY = object.getPosY();

//        hc = setImage();
        hitbox = new Circle(Operations.centerRect(posX, posX + 16), Operations.centerRect(posY, posY + 16), 8);
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {
//            gfx.drawImage(sG, posX, posY, null);
            gfx.setColor(Color.BLUE);
            gfx.drawRect(posX, posY, 16, 16);
        }
    }
}
