package org.academiadecodigo.spaceinvaders.game_entities.ship_features;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.gameplay.Operations;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.game_entities.Weapons;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProtonTorpedo<T extends GameObjects> extends GameObjects implements Weapons {

    private T object;
    private BufferedImage torpedo;
    private BufferedImage flare;
    private BufferedImage explosion;
    private GameBrain window;
    private final int maxSpeed = 8;
    private int animationCounter;
    private int torpedoMomentumCounter;
    private int explosionCounter;
    private int explosionFrames;
    private boolean onContact;
    private boolean onHit;
    private Rectangle tHitbox;
    private Circle exHitbox;


    public ProtonTorpedo(T object, int posY, int posX) {
        super();
        setPosX(posX);
        setPosY(posY);
        this.maxPosX = posX + 24;
        this.maxPosY = posY + 24;
        this.object = object;
        speed = 5;
        animationCounter = 0;
        explosionCounter = 0;
        explosionFrames = 0;
        onContact = false;
        onHit = false;

        if (object.isEnemy()) {
            tHitbox = new Rectangle(posX + 7, posY, 17, 24);
        } else {
            tHitbox = new Rectangle(posX - 7, posY, 17, 24);
        }
        exHitbox = new Circle(Operations.centerRect(posX, maxPosX), Operations.centerRect(posY-100, maxPosY - 70), explosionRadius());
    }
    @Override
    public void move() {

        if (explosionCounter > 48) {
            isDestroyed = true;
        }
        if(object.isEnemy()) {
            if (posX < -5) {
                onContact = true;
            }
            if (onContact) {
                exHitbox.setRadius(explosionRadius());
            } else {
                posX -= speed;
                maxPosX -= speed;

                tHitbox.setX(posX + 7);
                exHitbox.setCenterX(posX);

            }
        } else {
            if (posX >= 1360) {
                onContact = true;
            }
            if (onContact) {
                exHitbox.setRadius(explosionRadius());
            } else {
                posX += speed;
                maxPosX += speed;

                tHitbox.setX(posX - 7);
                exHitbox.setCenterX(posX);

            }
        }
        torpedoMomentum();
    }

    private void torpedoMomentum() {
        if(torpedoMomentumCounter < 56) {
            torpedoMomentumCounter++;
        }
        switch (torpedoMomentumCounter) {
            case 30, 45, 50 -> speed++;
        }
        if(torpedoMomentumCounter> 50) {
            speed++;
        }
    }

    private int explosionRadius() {

        if(onContact) {
            explosionCounter++;

            if(explosionCounter <= 15) {
                return (3 * explosionCounter);
            }
            if(explosionCounter < 20) {
                return 60;
            }
            if(explosionCounter < 25) {
                return 40;
            }
            if(explosionCounter < 30) {
                return 16;
            }
            if(explosionCounter < 38) {
                return 8;
            }
        }
        return 1;
    }

    public String explosionAnimation() {

        String explosionFrame = "";
        if(onContact) {
            if(explosionFrames < 48) {
                explosionFrames++;
                explosionFrame = "/explosion" + explosionFrames + ".png";
            }
        }
        return explosionFrame;
    }
    public String animationFlare() {
        String flare = "";
        if(object.isEnemy()) {
            if (animationCounter <= 15) {
                flare = "/PT_Background1.png";
            }
            if (animationCounter > 15) {
                flare = "/PT_Background2.png";
            }
        } else {
            if (animationCounter <= 15) {
                flare = "/PT_Background3.png";
            }
            if (animationCounter > 15) {
                flare = "/PT_Background4.png";
            }
        }
        return flare;
    }

    public void animationLogic() {
        animationCounter++;
        if (animationCounter > 30) {
            animationCounter = 0;
        }
    }

    public Circle getExHitbox() {
        return exHitbox;
    }

    public Rectangle getTHitbox() {
        return tHitbox;
    }

    public boolean isOnContact() {
        return onContact;
    }

    public void setOnContact(boolean onContact) {
        this.onContact = onContact;
    }

    public boolean isOnHit() {
        return onHit;
    }

    public void setOnHit(boolean onHit) {
        this.onHit = onHit;
    }


    @Override
    public void draw(Graphics2D gfx) {

        if(!isDestroyed) {
            animationLogic();


             if(onContact) {
                 explosion = setImage(explosionAnimation());
                 if(explosionFrames < 16) {
                     gfx.drawImage(explosion, posX - 82, posY - 82, null);
                 }
                 if(explosionFrames == 17) {
                     gfx.drawImage(explosion, posX - 92, posY - 92, null);
                 }
                 if(explosionFrames > 17) {
                     gfx.drawImage(explosion, posX - 103, posY - 103, null);
                 }

             } else {
                 flare = setImage(animationFlare());
                 if(object.isEnemy()) {
                     torpedo = setImage("/FPT.png");
                     gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                     gfx.drawImage(flare, posX, posY - 5, null);

                     gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                     gfx.drawImage(torpedo, posX, posY, null);
                 } else {
                     torpedo = setImage("/MFPT.png");
                     gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                     gfx.drawImage(flare, posX - 18, posY - 5, null);

                     gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                     gfx.drawImage(torpedo, posX, posY, null);
                 }
            }
        }
    }
}
