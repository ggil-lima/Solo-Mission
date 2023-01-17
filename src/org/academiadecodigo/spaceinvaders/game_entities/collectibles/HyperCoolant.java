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

        hc = setImage("/Coolant.png");
        hitbox = new Circle(Operations.centerRect(posX, posX + 32), Operations.centerRect(posY, posY + 32), 14);
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {
            gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if(flicker) {
                gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            gfx.drawImage(hc, posX, posY, null);
        }
    }

}
