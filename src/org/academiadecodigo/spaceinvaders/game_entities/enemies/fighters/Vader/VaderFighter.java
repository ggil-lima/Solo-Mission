package org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.Vader;

import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VaderFighter extends GameObjects {

    private boolean standardPattern;
    private boolean laserFlurry;
    private boolean forceStun;
    private boolean attack;
    private boolean moveUp;
    private boolean moveDown;
    private boolean centering;
    private BufferedImage vader;
    private int vaderCounter = 0;
    private int currentSpeed;
    private GameBrain window;
    private int centerVader;

    private Rectangle hitbox;



    public VaderFighter (GameBrain window)
    {
        super();
        this.window = window;
        centering = true;
        attack = false;
        moveUp = true;
        moveDown = false;
        isEnemy = true;

        speed = 5;
        currentSpeed = speed;
        lives = 30;
        setPosX(2360);
        setPosY(341);
//        setPosX(930);
        setMaxPosX(posX + 62);
        setMaxPosY(posY + 87);
        centerVader = Operations.centerRect(posY, maxPosY);
        vader = setImage("/vader.png");
        hitbox = new Rectangle(posX + 15, posY + 1, 52, 84);
    }

    public void enters() {
        if(posX > 930) {
            posX -= currentSpeed;
            maxPosX -= currentSpeed;
            hitbox.setX(posX + 15);
        }
    }


    public void vaderMovement() {
        if(!centering) {
            if (lives <= 25 && lives > 5) {
                currentSpeed = speed + 2;
            }
            if (lives <= 5) {
                currentSpeed = speed + 4;
            }
        }

        if (moveUp) {
            posY -= currentSpeed;
            maxPosY -= currentSpeed;
            centerVader = Operations.centerRect(posY, maxPosY);
            hitbox.setY(posY + 1);
            if (posY < 51) {
                moveUp = false;
                moveDown = true;
            }
        }
        if (moveDown) {
            posY += currentSpeed;
            maxPosY += currentSpeed;
            centerVader = Operations.centerRect(posY, maxPosY);
            hitbox.setY(posY + 1);
            if (maxPosY > 759) {
                moveDown = false;
                moveUp = true;
            }
        }
    }



    public void prepareAttack () {

        if(centering) {
            if (centerVader == 384) {
                currentSpeed = speed;
                vaderCounter = 0;
                attack = true;
                centering = false;
            }
        }
        if (!attack) {
            centering = true;
            centerVader();
        }
    }


    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void centerVader() {

        if(centerVader != 384) {
            vaderCounter++;
            if (centerVader > window.getHeight() / 2 && !attack) {
                if (moveDown) {
                    moveDown = false;
                    moveUp = true;
                }
                if (currentSpeed > 1) {
                    switch (vaderCounter) {
                        case 1, 30, 50, 70, 75, 78, 79 -> {
                            currentSpeed--;
                        }
                    }
                    posY -= currentSpeed;
                    maxPosY -= currentSpeed;
                    centerVader = Operations.centerRect(posY, maxPosY);
                    hitbox.setY(posY + 1);
                }
                if (currentSpeed == 1) {
                    posY -= currentSpeed;
                    maxPosY -= currentSpeed;
                    centerVader = Operations.centerRect(posY, maxPosY);
                    hitbox.setY(posY + 1);
                }
            }
            if (centerVader < window.getHeight() / 2 && !attack) {
                if (moveUp) {
                    moveDown = true;
                    moveUp = false;
                }
                if (currentSpeed > 1) {
                    switch (vaderCounter) {
                        case 1, 30, 50, 70, 75, 78, 79 -> {
                            currentSpeed--;
                        }
                    }
                    posY += currentSpeed;
                    maxPosY += currentSpeed;
                    centerVader = Operations.centerRect(posY, maxPosY);
                    hitbox.setY(posY + 1);
                }
                if (currentSpeed == 1) {
                    posY += currentSpeed;
                    maxPosY += currentSpeed;
                    centerVader = Operations.centerRect(posY, maxPosY);
                    hitbox.setY(posY + 1);
                }
            }
        }
    }

    public void draw(Graphics2D gfx) {
        if(!isDestroyed) {

            gfx.drawImage(vader, posX, posY, null);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
