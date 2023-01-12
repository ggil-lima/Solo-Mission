package org.academiadecodigo.spaceinvaders.game_panel.menus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameOverMenu {

    private BufferedImage defeat;

    public GameOverMenu() throws IOException {
        defeat = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/GameOver.png")));
    }

    public void draw(Graphics2D gfx) {

        gfx.drawImage(defeat, 0, 80,  null);


        gfx.dispose();
    }
}
