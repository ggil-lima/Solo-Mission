package org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.spaceinvaders.game_entities.GameObjects;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spaceship extends GameObjects {

    private BufferedImage millenniumFalcon;
    private GameBrain window;
    private Rectangle hitbox;
    private final int maxLives = 6;
    private final int maxSpeed = 8;
    private final int defaultSpeed;
    private final int defaultBackSpeed;
    private int backSpeed;
    private int cannonHeat;
    private int torpedoCharges;
    private final int defaultCannonHeat;
    private final int cannonOverheat;
    private int throttle = 0;
    private int inertia = 0;
    private int overheatCounter = 0;
    private int coolerCounter = 0;
    private int cooldownCounter;
    private final int defaultCooldown;

    private boolean canMoveUp;
    private boolean canMoveDown;
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean torpedoesEngaged;
    private boolean isHyperCooled;
    private boolean isOverHeated;
    private boolean isShooting;
    private boolean DeflectorShield;


    public Rectangle getHitbox() {
        return hitbox;
    }

    public Spaceship(GameBrain window) {
        super();
        this.window = window;
        lives = maxLives;
        speed = 0;
        backSpeed = 2;
        cannonHeat = 0;
        torpedoCharges = 3;
        defaultSpeed = speed;
        defaultBackSpeed = backSpeed;
        defaultCannonHeat = cannonHeat;
        cannonOverheat = 5;
        cooldownCounter = 24;
        defaultCooldown = cooldownCounter;
        canMoveUp = true;
        canMoveDown = true;
        canMoveLeft = true;
        canMoveRight = true;
        torpedoesEngaged = false;
        isHyperCooled = false;
        isOverHeated = false;
        isShooting = false;
        DeflectorShield = true;
        millenniumFalcon = setImage("/spaceship2.png");
        setPosX(16);
        setPosY(384);
        setMaxPosX(posX + 116);
        setMaxPosY(posY + 96);

        hitbox = new Rectangle(posX,posY, 90, 96);
    }

    public void moveSShipUp() {
        borderBlock();
        if(speed > maxSpeed) {
            speed = maxSpeed;
        }
        if (inertia > 0) {
            inertia = 0;
        }
        throttle++;
        switch (throttle) {
            case 1, 4, 7, 10, 12, 14, 15, 16 -> {
                speed++;
                posY -= speed;
                maxPosY -=speed;
                hitbox.setY(posY);
            }
        }
        if (throttle > 16) {
            posY -= speed;
            maxPosY -=speed;
            hitbox.setY(posY);
        }
    }

    public void moveSShipDown() {
        borderBlock();
        if (inertia > 0) {
            inertia = 0;
        }
        if(speed > maxSpeed) {
            speed = maxSpeed;
        }
        throttle++;
        switch (throttle) {
            case 1, 4, 7, 10, 12, 14, 15, 16 -> {
                speed++;
                posY += speed;
                maxPosY +=speed;
                hitbox.setY(posY);
            }
        }
        if (throttle > 16) {
            posY += speed;
            maxPosY +=speed;
            hitbox.setY(posY);
        }
}

    public void moveSShipLeft() {
        borderBlock();

        if (inertia > 0) {
            inertia = 0;
        }

        backSpeed = defaultBackSpeed;
        posX -= backSpeed;
        maxPosX -= backSpeed;
        hitbox.setX(posX);

    }

    public void moveSShipRight() {
        borderBlock();
        if(speed > maxSpeed) {
            speed = maxSpeed;
        }
        if (inertia > 0) {
            inertia = 0;
        }
        throttle++;
        switch (throttle) {
            case 1, 7, 13, 16, 19, 21, 22, 23 -> {
                speed++;
                posX += speed;
                maxPosX += speed;
                hitbox.setX(posX);
            }
        }
        if (throttle > 23) {
            posX += speed;
            maxPosX += speed;
            hitbox.setX(posX);
        }
    }

    public void moveUpReset() {
        borderBlock();
        if (throttle > 0) {
            throttle = 0;
        }
        if(speed > 0) {
            inertia++;
            switch (inertia) {
                case 1, 4, 7, 10, 12, 14, 15, 16 -> {
                    speed--;
                    posY -= speed;
                    maxPosY -= speed;
                    hitbox.setY(posY);
                }
            }
        }
    }

    public void moveDownReset() {
        borderBlock();
        if (throttle > 0) {
            throttle = 0;
        }
        if (speed > 0) {
            inertia++;
            switch (inertia) {
                case 1, 4, 7, 10, 12, 14, 15, 16 -> {
                    speed--;
                    posY += speed;
                    maxPosY += speed;
                    hitbox.setY(posY);
                }
            }
        }
    }

    public void moveLeftReset() {
        borderBlock();
        if (throttle > 0) {
            throttle = 0;
        }
        if(backSpeed > 0) {
            inertia++;
            switch (inertia) {
                case 1, 7, 13, 16 -> {
                    backSpeed--;
                    posX -= backSpeed;
                    maxPosX -= backSpeed;
                    hitbox.setX(posX);
                }
            }
        }
    }

    public void moveRightReset() {
        borderBlock();
        if (throttle > 0) {
            throttle = 0;
        }
        if(speed > 0) {
            inertia++;
            switch (inertia) {
                case 1, 4, 7, 10, 12, 14, 15, 16 -> {
                    speed--;
                    posX += speed;
                    maxPosX += speed;
                    hitbox.setX(posX);
                }
            }
        }
    }

    public void cannonHeatLogic() {
        if(!isHyperCooled) {
            coolerCounter = 0;
            if (cannonHeat == cannonOverheat) {
                isOverHeated = true;
            }
            if (isOverHeated) {
                overheatCounter++;
                if (overheatCounter % 48 == 0) {
                    cannonHeat--;
                    if (overheatCounter == 240) {
                        isOverHeated = false;
                        overheatCounter = 0;
                    }
                }
            }
            if (!isOverHeated && !isShooting()) {
                if (cannonHeat > 0) {
                    cooldownCounter--;
                    if (cooldownCounter <= 0) {
                        cooldownCounter = defaultCooldown;
                        cannonHeat--;
                    }
                }
            }
        } else {
            coolerCounter++;
            cannonHeat = 0;
            if(coolerCounter > 300) {
                isHyperCooled = false;
            }
        }
    }

    public void borderBlock(){
        if(posY < 50) {
            canMoveUp = false;
        }
        else {
            canMoveUp = true;
        }
        if(maxPosY > 758) {
            canMoveDown = false;
        }
        else {
            canMoveDown = true;
        }
        if(posX < 16) {
            canMoveLeft = false;
        }
        else {
            canMoveLeft = true;
        }
        if(maxPosX > 564) {
            canMoveRight = false;
        }
        else {
            canMoveRight = true;
        }
    }

    public void draw(Graphics2D gfx) {
        gfx.drawImage(millenniumFalcon, posX, posY, null);
    }

    public void cannonHeatToll(){
        cannonHeat++;
    }

    public void resetLives() {
        this.lives = maxLives;
    }
    public int getMaxLives() {
        return maxLives;
    }

    public void majorBoost() {
        lives++;
        lives++;
        lives++;
        if (lives > maxLives) {
            lives = maxLives;
        }

    }
    public void minorBoost() {
        lives++;
        if (lives > maxLives) {
            lives = maxLives;
        }

    }

    public int getCannonHeat() {
        return cannonHeat;
    }

    public int getCannonOverheat() {
        return cannonOverheat;
    }

    public boolean canMoveUp() {
        return canMoveUp;
    }

    public boolean canMoveDown() {
        return canMoveDown;
    }

    public boolean canMoveLeft() {
        return canMoveLeft;
    }

    public boolean canMoveRight() {
        return canMoveRight;
    }

    public boolean isTorpedoesEngaged() {
        return torpedoesEngaged;
    }
    public void setTorpedoesEngaged(boolean torpedoesEngaged) {
        this.torpedoesEngaged = torpedoesEngaged;
    }

    public int getTorpedoCharges() {
        return torpedoCharges;
    }



    public boolean isHyperCooled() {
        return isHyperCooled;
    }

    public void setHyperCooled(boolean hyperCooled) {
        isHyperCooled = hyperCooled;
    }

    public void addTorpedoCharge() {
        torpedoCharges++;

        if (torpedoCharges > 3) {
            torpedoCharges = 3;
        }
    }

    public void shootTorpedo() {
        torpedoCharges--;

        if (torpedoCharges <= 0) {
            torpedoCharges = 0;
            torpedoesEngaged = false;
        }
    }

    public boolean isOverHeated() {
        return isOverHeated;
    }

    public void setOverHeated(boolean overHeated) {
        isOverHeated = overHeated;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public boolean isDeflectorShield() {
        return DeflectorShield;
    }


    public void setDeflectorShield(boolean DeflectorShield) {
        this.DeflectorShield = DeflectorShield;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
}
