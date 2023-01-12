package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;

import java.awt.*;

public interface Collectibles {

    void behaviour();

    void animation();

    Circle getHitbox();

    void setIsHit (boolean b);

    void draw(Graphics2D gfx);

    boolean isDestroyed();

}
