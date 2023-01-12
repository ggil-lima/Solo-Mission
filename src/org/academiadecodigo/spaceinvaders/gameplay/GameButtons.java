package org.academiadecodigo.spaceinvaders.gameplay;

import org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon.Spaceship;
import org.academiadecodigo.spaceinvaders.gameplay.GameBrain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameButtons implements KeyListener {
    private Spaceship sShip;
    private GameBrain game;



    public GameButtons (Spaceship sShip, GameBrain game) {
        this.sShip = sShip;
        this.game = game;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.gameState == game.startState) {
            startControls(e);
        }
        else if (game.gameState == game.cinematicState) {
            csControls(e);
        }
        else if (game.gameState == game.overState) {
            overControls(e);
        }
        else if (game.gameState == game.playState) {
            playControls(e);
        }
        else if (game.gameState == game.pauseState) {
            pauseControls(e);
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();

        if (game.gameState == game.playState) {
            if (key == KeyEvent.VK_SPACE) {
                sShip.setShooting(false);
            }
            if (key == KeyEvent.VK_UP) {
                if(sShip.canMoveUp()) {
                    game.setMovingUp(false);
                    game.setMovingUpReset(true);
                } else {
                    game.setMovingUp(false);
                    game.setMovingUpReset(false);
                }
            }
            if (key == KeyEvent.VK_DOWN) {
                if(sShip.canMoveDown()) {
                    game.setMovingDown(false);
                    game.setMovingDownReset(true);
                } else {
                    game.setMovingDown(false);
                    game.setMovingDownReset(false);
                }
            }
            if (key == KeyEvent.VK_LEFT) {
                if(sShip.canMoveLeft()) {
                    game.setMovingLeft(false);
                    game.setMovingLeftReset(true);
                } else {
                    game.setMovingLeft(false);
                    game.setMovingLeftReset(false);
                }
            }
            if (key == KeyEvent.VK_RIGHT) {
                if(sShip.canMoveRight()) {
                    game.setMovingRight(false);
                    game.setMovingRightReset(true);
                } else {
                    game.setMovingRight(false);
                    game.setMovingRightReset(false);

                }
            }
        }
    }

    public void startControls(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            game.setStartGame(true);
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (key == KeyEvent.VK_TAB) {
            //something help
        }
    }

    public void pauseControls(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER) {
            game.gameState = game.playState;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void csControls(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    public void playControls(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            if(sShip.canMoveUp()) {
                game.setMovingUp(true);
                game.setMovingUpReset(false);
            } else {
                game.setMovingUp(false);
                game.setMovingUpReset(false);
            }
        }
        if (key == KeyEvent.VK_DOWN) {
            if(sShip.canMoveDown()) {
                game.setMovingDown(true);
                game.setMovingDownReset(false);
            } else {
                game.setMovingDown(false);
                game.setMovingDownReset(false);
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(sShip.canMoveRight()) {
                game.setMovingRight(true);
                game.setMovingRightReset(false);
            } else {
                game.setMovingRight(false);
                game.setMovingRightReset(false);
            }
        }
        if (key == KeyEvent.VK_LEFT) {
            if(sShip.canMoveLeft()) {
                game.setMovingLeft(true);
                game.setMovingLeftReset(false);
            } else {
                game.setMovingLeft(false);
                game.setMovingLeftReset(false);
            }
        }
        if (key == KeyEvent.VK_SPACE) {
            sShip.setShooting(!sShip.isOverHeated());
        }
        if (key == KeyEvent.VK_D) {
            if(sShip.getTorpedoCharges() == 0) {
                game.setNoChargesSFX(true);
            } else {
                sShip.setTorpedoesEngaged(!sShip.isTorpedoesEngaged());
            }
        }
        if (key == KeyEvent.VK_ENTER) {
            game.gameState = game.pauseState;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    public void overControls(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (key == KeyEvent.VK_SPACE) {
            game.gameState = game.startState;
        }
    }

}
