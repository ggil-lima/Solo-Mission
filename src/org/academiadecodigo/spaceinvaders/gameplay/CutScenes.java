package org.academiadecodigo.spaceinvaders.gameplay;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CutScenes {

    private BufferedImage han, chewie, text1, text2, text3, text4;
    private int cCounter = 0;


    public void getImages () {
        try {
            han = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hanSolo.gif")));
            //            (340, 256, "Resources/hanSolo.gif");
            chewie = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/chewbacca.png")));
            //            new Picture(852, 256, "Resources);
            text1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/text1.png")));
            //        new Picture(540, 430, "Resources/text1.png");
            text2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/text2.png")));
            //            new Picture(540, 430, "Resources/text2.png");
            text3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/text3.png")));
            //            new Picture(620, 430, "Resources/text3.png");
            text4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/text4.png")));
            //            new Picture(540, 430, "Resources/text4.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void intro (Graphics2D gfx) {

        getImages();


        if (cCounter < (3.5 * 60)) {
            showTextBox(gfx);
            gfx.drawImage(text1, 540, 430, null);
            gfx.drawImage(han, 340, 256, null);
            cCounter++;
        }
        if (cCounter >= (3.5 * 60) && cCounter < (5.5 * 60)) {
            showTextBox(gfx);
            gfx.drawImage(text3, 620, 430, null);
            gfx.drawImage(chewie, 852, 256, null);
            cCounter++;
        }
        if (cCounter >= (5.5 * 60) && cCounter < (8 * 60)) {
            showTextBox(gfx);
            gfx.drawImage(han, 340, 256, null);
            gfx.drawImage(text2, 540, 430, null);
            cCounter++;
        }
        if (cCounter >= (8 * 60) && cCounter < (9.5 * 60)) {
            showTextBox(gfx);
            gfx.drawImage(han, 340, 256, null);
            gfx.drawImage(text4, 540, 430, null);
            cCounter++;
        }
    }

    public synchronized void enterDeathStar(Graphics2D gfx) {

        if (cCounter < (3.5 * 60)) {
            showTextBox(gfx);
//            gfx.drawImage(text1, 540, 430, null);
            gfx.drawImage(han, 340, 256, null);
            cCounter++;
        }
        if (cCounter >= (3.5 * 60) && cCounter < (5.5 * 60)) {
            showTextBox(gfx);
//            gfx.drawImage(text3, 620, 430, null);
            gfx.drawImage(chewie, 852, 256, null);
            cCounter++;
        }
        if (cCounter >= (5.5 * 60) && cCounter < (8 * 60)) {
            showTextBox(gfx);
            gfx.drawImage(han, 340, 256, null);
//            gfx.drawImage(text2, 540, 430, null);
            cCounter++;
        }


    }

    public void showTextBox (Graphics2D gfx) {
        gfx.setColor(new Color(150,150,150));
        gfx.fillRect(315, 241, 734, 286);

        gfx.setColor(new Color(100,100,100));
        gfx.fillRect(320, 246, 724, 276);

        gfx.setColor(new Color(255,255,255));
        gfx.fillRect(520, 414, 340, 96);
    }

    public void resetCSCounter () {
        cCounter = 0;
    }




}
