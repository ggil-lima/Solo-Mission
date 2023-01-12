package org.academiadecodigo.spaceinvaders.game_panel.menus;

import org.academiadecodigo.spaceinvaders.game_panel.Background;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartMenu {


    private BufferedImage starWars;
    private BufferedImage pressSpace;




    public StartMenu() throws IOException {

        starWars = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/starwars.png")));
        pressSpace = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pressSpaceStart.png")));

    }


    public void drawLogo(Graphics2D gfx) {

        gfx.drawImage(starWars, 0, 0,  null);


        gfx.dispose();
    }

    public void drawPressSpace(Graphics2D gfx) {
        gfx.drawImage(pressSpace, 0, 0 ,null);
    }
}

