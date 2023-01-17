package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args)  {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Star Wars: Solo Mission");

        GameBrain newGame = new GameBrain();
        window.add(newGame);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        newGame.start();
    }
}
