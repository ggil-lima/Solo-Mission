package org.academiadecodigo.spaceinvaders.game_entities.collectibles;

import javafx.scene.shape.Circle;

import java.awt.*;

public interface Collectibles {

    void behaviour();

    void animation(Graphics2D gfx);

    Circle getHitbox();

    void fading();

    void setIsHit (boolean b);

    void draw(Graphics2D gfx);

    boolean isDestroyed();

}
