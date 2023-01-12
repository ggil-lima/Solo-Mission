package org.academiadecodigo.spaceinvaders.gameplay;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CollisionChecker {

    public static boolean collides(Circle c1, Circle c2) {
        return Math.pow(c1.getCenterX() - c2.getCenterX(), 2) + Math.pow(c1.getCenterY() - c2.getCenterY(), 2) <= Math.pow(c1.getRadius() + c2.getRadius(), 2);
    }

    public static boolean collides(Rectangle r1, Rectangle r2) {
        return (r1.getX() < r2.getX() + r2.getWidth() && r1.getX() + r1.getWidth() > r2.getX() && r1.getY() < r2.getY() + r2.getHeight() && r1.getHeight() + r1.getY() > r2.getY());
    }

    public static boolean collides(Circle c1, Rectangle r1) {
        float closestX = clamp((float) c1.getCenterX(), (float) r1.getX(), (float) (r1.getX() + r1.getWidth()));
        float closestY = clamp((float) c1.getCenterY(), (float) (r1.getY() - r1.getHeight()), (float) r1.getY());

        float distanceX = (float) (c1.getCenterX() - closestX);
        float distanceY = (float) (c1.getCenterY() - closestY);

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(c1.getRadius(), 2);
    }

    public static float clamp(float value, float min, float max) {
        float x = value;
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }
}
