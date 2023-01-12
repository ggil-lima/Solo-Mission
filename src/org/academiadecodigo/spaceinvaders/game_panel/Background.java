package org.academiadecodigo.spaceinvaders.game_panel;

import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Background {

    private final GameBrain window;
    private BufferedImage background;

    public Background (GameBrain window) {
        this.window = window;

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw (Graphics2D g) {
        g.drawImage(background,0,0,window.getWidth(), window.getHeight(), null);
    }
}
