package org.academiadecodigo.spaceinvaders.gameplay;


import org.academiadecodigo.spaceinvaders.game_entities.collectibles.*;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star.BeamEye;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star.SuperLaser;
import org.academiadecodigo.spaceinvaders.game_entities.ship_features.DeflectorShield;
import org.academiadecodigo.spaceinvaders.game_entities.ship_features.ProtonTorpedo;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.Vader.VaderLasers;
import org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon.MFLasers;
import org.academiadecodigo.spaceinvaders.game_entities.millennium_falcon.Spaceship;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.death_star.DeathStar;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.EnemyFighter;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.EnemyLaser;
import org.academiadecodigo.spaceinvaders.game_entities.enemies.fighters.Vader.VaderFighter;
import org.academiadecodigo.spaceinvaders.game_panel.Background;
import org.academiadecodigo.spaceinvaders.game_panel.menus.GameOverMenu;
import org.academiadecodigo.spaceinvaders.game_panel.menus.StartMenu;
import org.academiadecodigo.spaceinvaders.game_panel.UI;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameBrain extends JPanel implements Runnable{

    //SCREEN SETTINGS
    private final int cellSize = 16;
    private final int scaledCellSize = 48;
    private final int cols = 85;
    private final int rows = 48;
    private final int width = cols * cellSize;
    private final int height = rows * cellSize;


    private Thread gameThread;

    //ENTITIES
    private Spaceship sShip;
    private DeflectorShield<Spaceship> mShield;
    private VaderFighter vader;
    private DeflectorShield<VaderFighter> vShield;
    private DeathStar dS;
    private BeamEye eye;
    private SuperLaser sLaser;

    //CUTSCENES
    private CutScenes cs;

    //GAME EXPERIENCE
    private Background background;
    private Sound sound;
    private UI ui;

    //CONTROLS
    private GameButtons keys;

    //MENUS
    private StartMenu startMenu;
    private GameOverMenu gameOver;

    //LISTS
    private LinkedList<EnemyFighter> fighters;
    private LinkedList<MFLasers> mfLasers;
    private LinkedList<EnemyLaser> enemyLasers;
    private LinkedList<VaderLasers> vaderLasers;
    private LinkedList<ProtonTorpedo> vaderPTs;
    private LinkedList<ProtonTorpedo> millenniumPTs;
    private LinkedList<Collectibles> collectibles;

    //BOOLEANS
    private boolean startGame;
    private boolean finishCS;
    private boolean canMoveDs;
    private boolean isPaused;
    private boolean startMusic;

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private boolean movingUpReset, movingDownReset, movingLeftReset, movingRightReset;
    private boolean torpedoSFX;
    private boolean noChargesSFX;


    private boolean standardPattern;
    private boolean laserFlurry;
    private boolean vDeflectorShield;
    private boolean forceStun;
    private boolean vaderDefeated;
    private boolean dsDefeated;

    //FPS
    private final int FPS = 60;

    //COUNTERS
    private int frameCounter;
    private int enemyCounter;
    private int toggleCounter;
    private int switchCounter;
    private int cooldownCounter;
    private int criticalCounter;
    private int overheatCounter;
    private int tCooldowCounter;

    //GAME STATES
    public int gameState;
    public final int pauseState = 0;
    public final int startState = 1;
    public final int playState = 2;
    public final int overState = 3;
    public final int cinematicState = 4;

    //GAME PHASES

    public int gamePhase;
    public final int preparePhase1 = 1;
    public final int beginPhase1 = 2;
    public final int preparePhase2 = 3;
    public final int beginPhase2 = 4;
    public final int preparePhase3 = 5;
    public final int beginPhase3 = 6;
    public final int gameOverPhase = 7;
    public final int victoryPhase = 8;


    public GameBrain() {

        //WINDOW
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        background = new Background(this);

        //MENUS
        try {
            startMenu = new StartMenu();
            gameOver = new GameOverMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //MILLENNIUM FALCON
        sShip = new Spaceship(this);
        mShield = new DeflectorShield<>(sShip);

        //ENEMIES
        dS = new DeathStar(this);
        eye = new BeamEye();
        sLaser = new SuperLaser(dS);
        vader = new VaderFighter(this);
        vShield = new DeflectorShield<>(vader);

        //GAME EXPERIENCE
        ui = new UI(this, sShip);
        sound = new Sound();
        cs = new CutScenes();

        //CONTROLS
        keys = new GameButtons(sShip,this);
        this.addKeyListener(keys);
        this.setFocusable(true);

        //DEFAULT VALUES
        setDefaultValues();

        //SETTING INITIAL STATE
        gameState = startState;
    }

    public void start(){
        gameThread = new Thread(this);
        gameThread.start();

    }public void setDefaultValues() {
        //BOOLEANS
        startGame = false;
        startMusic = false;
        isPaused = false;
        finishCS = false;
        canMoveDs = false;
        vaderDefeated = false;
        dsDefeated = false;

        //MILLENNIUM FALCON
        defaultMovement();
        torpedoSFX = true;
        noChargesSFX = false;

        //COUNTERS
        frameCounter = 0;
        enemyCounter = 0;
        toggleCounter = 0;
        switchCounter = 0;
        cooldownCounter = 0;
        criticalCounter = 0;
        tCooldowCounter = 50;
        cs.resetCSCounter();

        //PLAYER POSITION
        sShip.setPosX(16);
        sShip.setPosY(384);
        sShip.setMaxPosX(132);
        sShip.setMaxPosY(480);

        //PLAYER STATS
        sShip.resetLives();
        ui.resetScore();

        //ENTITY LISTS
        fighters = new LinkedList<>();
        mfLasers = new LinkedList<>();
        millenniumPTs = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        vaderLasers = new LinkedList<>();
        vaderPTs = new LinkedList<>();
        collectibles = new LinkedList<>();

        //VADER
        setDefaultVader();
    }



    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime- lastTime)/drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }


    public void update() {
        updateLists();
        phaseChecker();

        //START STATE
        if(gameState == startState) {
            if (toggleCounter == 61) {
                toggleCounter = 0;
            }
            toggleCounter++;
            if (startGame) {
                gamePhase = preparePhase1;
                gameState = cinematicState;
//                gamePhase = beginPhase2;
//                gameState = playState;
            }
        }

        //PLAY STATE
        if(gameState == playState) {
            if(isPaused) {
                isPaused = false;
            }
            if(sShip.getLives() == 0) {
             gameState = overState;
            }

            //COLLISION
            hitSShip();
            hitCollectible();

            //WEAPONS
            spawnSBullets();
            moveBullets();
            shieldManager();
            spawnSTorpedoes();
            moveSTorpedoes();

            //MECHANICS
            playerMovementController();
            sShip.cannonHeatLogic();

            //PHASE1
            if (gamePhase == beginPhase1) {
                //COUNTERS
                frameCounter++;
                if (frameCounter > 300) {
                    frameCounter = 0;
                }
                //COLLISION
                killEnemy();

                //TIE FIGHTERS
                spawnEFighters();
                moveEnemies();

                //BULLETS
                spawnEBullets();
                moveEnemyBullets();


            }

            //PHASE2
            if (gamePhase == beginPhase2) {

                //COUNTERS
                frameCounter++;

                //VADER
                if(!vaderDefeated) {
                    vaderPatterns();
                }
                moveVLasers();
                moveVTorpedoes();

                //COLLISION
                hitVader();
                hitTorpedo();
            }

            //PHASE3
            if (gamePhase == beginPhase3) {

                //COUNTERS
                frameCounter++;

                //TIE FIGHTERS
                spawnEFighters();
                moveEnemies();

                //BULLETS
                spawnEBullets();
                moveEnemyBullets();

                //DEATH STAR
                ReadySuperLaser();

                //COLLISION
                hitEye();
                killEnemy();

            }
        }

        //PAUSE STATE
        else if(gameState == pauseState) {
            isPaused = true;
        }

        //CINEMATIC STATE
        else if (gameState == cinematicState) {

            if(gamePhase == preparePhase1) {
                toggleCounter++;
                if (toggleCounter > (9.51 * 60)) {
                    finishCS = true;
                }
            }
            if (gamePhase == preparePhase2) {
                 if(canMoveDs) {
                     frameCounter++;
                     if (frameCounter % 2 == 0) {
                         dS.moveDS();
                         vader.enters();
                     }
                     if (frameCounter > 10 * 60) {
                         finishCS = true;
                     }
                 } else {
                     enemyCounter++;
                     if (enemyCounter > (8 * 61)) {
                         canMoveDs = true;
                         frameCounter = 0;
                     }
                 }
            }
            if (gamePhase == preparePhase3) {
                toggleCounter++;
                if (toggleCounter > 10 * 60) {
                    finishCS = true;
                }
            }

        //GAME OVER STATE
        } else if (gameState == overState) {
            setDefaultValues();
            setDefaultVader();
        }

        //SOUND MANAGER
        if(startMusic) {
            musicController();
            startMusic = false;
        }
        sfxController();
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gfx = (Graphics2D)g;
        background.draw(gfx);

        //START STATE
        if(gameState == startState) {
            if (!startGame) {
                if (toggleCounter <= 30) {
                    startMenu.drawPressSpace(gfx);
                }
            }
            startMenu.drawLogo(gfx);
        }

        //PLAY STATE
        if (gameState == playState) {

            //MILLENNIUM FALCON
            sShip.draw(gfx);
            if(sShip.isDeflectorShield()) {
                mShield.draw(gfx);
            }

            //TIE FIGHTERS
            if (gamePhase == beginPhase1) {
                drawEBullets(gfx);
                drawEFighters(gfx);
            }

            //VADER
            if (gamePhase == beginPhase2) {

                vader.draw(gfx);
                dS.draw(gfx);
                drawVLasers(gfx);
                if (vDeflectorShield) {
                    vShield.draw(gfx);
                }
                drawVTorpedoes(gfx);
            }

            //DEATH STAR
            if (gamePhase == beginPhase3) {
                dS.draw(gfx);
                eye.draw(gfx);

                drawEBullets(gfx);
                drawEFighters(gfx);

                if(sLaser.isIgnite()) {
                    sLaser.draw(gfx);
                }
            }

            //MILLENNIUM FALCON WEAPONS
            drawBullets(gfx);
            drawSTorpedoes(gfx);


            //UI
            ui.hpBar(gfx);
            ui.overheatBar(gfx);
            ui.drawScore(gfx);
            ui.drawTorpedoUI(gfx);

            //COLLECTIBLES
            drawCollectibles(gfx);
        }

        // PAUSE STATE
        else if(gameState == pauseState) {
            ui.drawPauseScreen(gfx);
        }

        //CINEMATIC STATE
        else if(gameState == cinematicState) {
            if (gamePhase == preparePhase1) {
                cs.intro(gfx);
            }
            if (gamePhase == preparePhase2) {
                cs.enterDeathStar(gfx);
                if (canMoveDs) {
                    vader.draw(gfx);
                    dS.draw(gfx);
                    sShip.draw(gfx);
                    if(sShip.isDeflectorShield()) {
                        mShield.draw(gfx);
                    }
                }
            }

            if (gamePhase == beginPhase2) {
                vader.draw(gfx);
                dS.draw(gfx);
                sShip.draw(gfx);
            }

            if (gamePhase == preparePhase3) {
                cs.enterDeathStar(gfx);
            }
        }

        //GAME OVER STATE
        else if(gameState == overState) {
            gameOver.draw(gfx);
        }

        gfx.dispose();
    }


    public synchronized void moveBullets() {
        for (MFLasers mfLasers : mfLasers) {
                mfLasers.move();
        }
    }

    public synchronized void moveEnemies() {
        for (EnemyFighter enemyFighter : fighters) {
            enemyFighter.moveFighter();
        }
    }

    public synchronized void vaderPatterns() {

        //PATTERN MANAGER
        if (frameCounter == 1) {
            standardPattern = true;
            laserFlurry = false;
        }
        if (frameCounter == 10 * 60) {
            vader.setAttack(false);
            standardPattern = false;
            laserFlurry = true;
            playSFX(8);
        }


        //SHIELD MANAGER
        if(vader.getLives() <= 25) {
            if(cooldownCounter > 0) {
            cooldownCounter--;
            }
            if (!vDeflectorShield && cooldownCounter == 0) {
                vShield.setIsDestroyed(false);
                vDeflectorShield = true;
            }
            if(vShield.isDestroyed()) {
                vDeflectorShield = false;
                vShield.resetLives();
                cooldownCounter = 300;
            }
            if(vDeflectorShield) {
                vShield.surround();
                vShield.regen();
                vShieldBounce();
            }
        }

        //STANDARD PATTERN
        if (standardPattern) {
            if (vader.isAttack()) {
                vader.vaderMovement();
                if (frameCounter % 60 == 0) {
                    vaderLasers.add(new VaderLasers(vader.getPosY() + 13, vader.getPosX() - 27, this));
                    vaderLasers.add(new VaderLasers(vader.getPosY() + 74, vader.getPosX() - 11, this));
                    playSFX(6);
                }
            } else {
                vader.prepareAttack();
            }
        }

        //LASER FLURRY
        if (laserFlurry) {
            if (vader.isAttack()) {
                enemyCounter++;
                vader.vaderMovement();
                if (enemyCounter > 0 && enemyCounter < 70 ||
                        enemyCounter > 120 && enemyCounter < 190 ||
                        enemyCounter > 240 && enemyCounter < 300) {
                    if (frameCounter % 5 == 0) {
                        vaderLasers.add(new VaderLasers(vader.getPosY() + 13, vader.getPosX() - 27, this));
                        vaderLasers.add(new VaderLasers(vader.getPosY() + 74, vader.getPosX() - 11, this));
                        playSFX(6);
                    }

                }
                if(vader.getLives() > 30) {
                    if (enemyCounter >= 300) {
                        enemyCounter = 0;
                        frameCounter = 0;
                        vader.setAttack(false);
                    }
                }
                if(vader.getLives() <= 30) {
                    if (enemyCounter >= 320 && enemyCounter < 490) {
                        if(enemyCounter % 80 == 0 || enemyCounter % 95 == 0) {
                            vaderPTs.add(new ProtonTorpedo<>(vader, availableTorpedoLauncher(), vader.getPosX()-23));
                            playSFX(1);
                        }
                    }
                    if (enemyCounter >= 490) {
                        enemyCounter = 0;
                        frameCounter = 0;
                        vader.setAttack(false);
                    }
                }
            } else  {
                vader.prepareAttack();

            }
        }
    }



    public synchronized void hitSShip(){

        if(gamePhase == beginPhase1 || gamePhase == beginPhase3) {
            for (EnemyLaser enemyLaser : enemyLasers) {
                if(CollisionChecker.collides(enemyLaser.getHitbox(), sShip.getHitbox())) {
                    enemyLaser.setIsDestroyed(true);
                    sShip.reduceLives();
                }
                if(sShip.isDeflectorShield()) {
                    if (CollisionChecker.collides(mShield.getHitbox(), enemyLaser.getHitbox())) {
                        enemyLaser.deflected();
                        playSFX(7);
                        mShield.reduceLives();
                    }
                }
            }
        }

        if(gamePhase == beginPhase2) {
            for (VaderLasers vLaser : vaderLasers) {
                if (CollisionChecker.collides(vLaser.getHitbox(), sShip.getHitbox())) {
                    vLaser.setIsDestroyed(true);
                    sShip.reduceLives();
                }
                if(sShip.isDeflectorShield()) {
                    if (CollisionChecker.collides(mShield.getHitbox(), vLaser.getHitbox())) {
                        vLaser.deflected();
                        playSFX(7);
                        mShield.reduceLives();
                    }
                }
            }

            for (MFLasers mFLasers : mfLasers) {
                if (mFLasers.isDeflected()) {
                    if (CollisionChecker.collides(mFLasers.getHitbox(), sShip.getHitbox())) {

                        mFLasers.setIsDestroyed(true);
                        sShip.reduceLives();
                    }
                    if(sShip.isDeflectorShield()) {
                        if (CollisionChecker.collides(mShield.getHitbox(), mFLasers.getHitbox())) {
                            mFLasers.reDeflected();
                            playSFX(7);
                            mShield.reduceLives();
                        }
                    }
                }
            }

            for(ProtonTorpedo torpedo : vaderPTs) {
                if(CollisionChecker.collides(torpedo.getExHitbox(), sShip.getHitbox())) {
                    if(!torpedo.isOnHit()) {
                        sShip.reduceLives();
                        torpedo.setOnHit(true);
                    }
                }
                if(CollisionChecker.collides(torpedo.getTHitbox(), sShip.getHitbox())) {
                    if(!torpedo.isOnHit()) {
                        torpedo.setOnContact(true);
                        playSFX(9);
                        sShip.reduceLives();
                        sShip.reduceLives();
                        torpedo.setOnHit(true);
                    }
                }
                if(sShip.isDeflectorShield()) {
                    if (CollisionChecker.collides(mShield.getHitbox(), torpedo.getTHitbox())) {
                        if (!torpedo.isOnHit()) {
                            torpedo.setOnContact(true);
                            playSFX(9);
                            mShield.setIsDestroyed(true);
                            torpedo.setOnHit(true);
                        }
                    }
                    if (CollisionChecker.collides(mShield.getHitbox(), torpedo.getExHitbox())) {
                        if (!torpedo.isOnHit()) {
                            mShield.reduceLives();
                            mShield.reduceLives();
                            mShield.reduceLives();
                            torpedo.setOnHit(true);
                        }
                    }
                }
            }
        }
    }

    public synchronized void updateLists() {

        ArrayList<MFLasers> toRemoveS = new ArrayList<>();
        ArrayList<Collectibles> toRemoveC = new ArrayList<>();
        ArrayList<ProtonTorpedo> toRemoveMT = new ArrayList<>();

        for(MFLasers mf : mfLasers) {
            if(mf.isDestroyed()) {
                toRemoveS.add(mf);
            }
        }
        mfLasers.removeAll(toRemoveS);
        for(int i = 0; i<toRemoveS.size(); i++) {
            toRemoveS.remove(i);
            i--;
        }

        for(Collectibles collectible : collectibles) {
            if(collectible.isDestroyed()) {
                toRemoveC.add(collectible);
            }
        }
        collectibles.removeAll(toRemoveC);
        for(int i = 0; i<toRemoveC.size(); i++) {
            toRemoveC.remove(i);
            i--;
        }

        for(ProtonTorpedo torpedo : millenniumPTs) {
            if(torpedo.isDestroyed()) {
                toRemoveMT.add(torpedo);
            }
        }
        millenniumPTs.removeAll(toRemoveMT);
        for(int i = 0; i<toRemoveMT.size(); i++) {
            toRemoveMT.remove(i);
            i--;
        }

        if(gamePhase == beginPhase1 || gamePhase == beginPhase3) {
            ArrayList<EnemyFighter> toRemoveE = new ArrayList<>();
            ArrayList<EnemyLaser> toRemoveEB = new ArrayList<>();

            for (EnemyFighter enemyFighter : fighters) {
                if (enemyFighter.isDestroyed() || enemyFighter.isOutside()) {
                    toRemoveE.add(enemyFighter);
                }
            }
            fighters.removeAll(toRemoveE);

            for(int i = 0; i<toRemoveE.size(); i++) {
                toRemoveE.remove(i);
                i--;
            }

            for (EnemyLaser laser : enemyLasers) {
                if (laser.isDestroyed()) {
                    toRemoveEB.add(laser);
                }
            }
            enemyLasers.removeAll(toRemoveEB);

            for(int i = 0; i<toRemoveEB.size(); i++) {
                toRemoveEB.remove(i);
                i--;
            }

        }
        if(gamePhase == beginPhase2) {
            ArrayList<VaderLasers> toRemoveVL = new ArrayList<>();
            ArrayList<ProtonTorpedo> toRemoveVT = new ArrayList<>();

            for (VaderLasers lasers : vaderLasers) {
                if (lasers.isDestroyed()) {
                    toRemoveVL.add(lasers);
                }
            }
            vaderLasers.removeAll(toRemoveVL);

            for(int i = 0; i<toRemoveVL.size(); i++) {
                toRemoveVL.remove(i);
                i--;
            }
            for (ProtonTorpedo torpedo : vaderPTs) {
                if (torpedo.isDestroyed()) {
                    toRemoveVT.add(torpedo);
                }
            }
            vaderPTs.removeAll(toRemoveVT);

            for(int i = 0; i<toRemoveVT.size(); i++) {
                toRemoveVT.remove(i);
                i--;
            }
        }
    }

    public synchronized void killEnemy() {
        for (EnemyFighter enemyFighter : fighters) {
            if(CollisionChecker.collides(sShip.getHitbox(), enemyFighter.getHitbox())){
                enemyFighter.setIsDestroyed(true);
                sShip.reduceLives();
                ui.addScore();
            }
            for (MFLasers lasers : mfLasers) {
                if(CollisionChecker.collides(lasers.getHitbox(), enemyFighter.getHitbox())){
                    lasers.setIsDestroyed(true);
                    dropsFighter(enemyFighter);
                    playSFX(3);
                    enemyFighter.setIsDestroyed(true);
                    ui.addScore();

                    if(lasers.isCriticalShot()) {
                        ui.addScore();
                    }
                }
            }
            for(ProtonTorpedo torpedo : millenniumPTs) {
                if(CollisionChecker.collides(torpedo.getExHitbox(), enemyFighter.getHitbox())){
                    if(!torpedo.isOnHit()) {
                        enemyFighter.setIsDestroyed(true);
                        dropsFighter(enemyFighter);
                        torpedo.setOnHit(true);
                        ui.addScore();
                    }
                }
                if(CollisionChecker.collides(torpedo.getTHitbox(), enemyFighter.getHitbox())) {
                    if(!torpedo.isOnHit()) {
                        torpedo.setOnContact(true);
                        playSFX(9);
                        enemyFighter.setIsDestroyed(true);
                        dropsFighter(enemyFighter);
                        torpedo.setOnHit(true);
                        ui.addScore();
                    }
                }
            }
            for(EnemyLaser eLaser : enemyLasers) {
                if(eLaser.isDeflected()) {
                    if(CollisionChecker.collides(eLaser.getHitbox(), enemyFighter.getHitbox())) {
                        enemyFighter.setIsDestroyed(true);
                        playSFX(3);
                        eLaser.setIsDestroyed(true);
                        ui.addScore();
                    }
                }
            }
        }
    }

    public synchronized void hitVader () {
        for (MFLasers lasers : mfLasers) {
            if(!lasers.isDeflected()) {
                if (CollisionChecker.collides(lasers.getHitbox(), vader.getHitbox())) {
                    lasers.setIsDestroyed(true);
                    playSFX(3);
                    vader.reduceLives();
                    if(lasers.isCriticalShot()) {
                        vader.reduceLives();
                        criticalCounter++;
                    }
                }
            }
        }
        for(ProtonTorpedo torpedo : millenniumPTs) {
            if(CollisionChecker.collides(torpedo.getExHitbox(), vader.getHitbox())){
                if(!torpedo.isOnHit()) {
                    vader.reduceLives();
                    torpedo.setOnHit(true);
                }
            }
            if(CollisionChecker.collides(torpedo.getTHitbox(), vader.getHitbox())) {
                if(!torpedo.isOnHit()) {
                    torpedo.setOnContact(true);
                    playSFX(9);
                    vader.reduceLives();
                    vader.reduceLives();
                    torpedo.setOnHit(true);
                }
            }
        }
        for(VaderLasers vLaser : vaderLasers) {
            if(vLaser.isDeflected()) {
                if(CollisionChecker.collides(vLaser.getHitbox(), vader.getHitbox())) {
                    vader.reduceLives();
                    playSFX(3);
                    vLaser.setIsDestroyed(true);
                }

                if(CollisionChecker.collides(vShield.getHitbox(), vLaser.getHitbox())) {
                    vLaser.reDeflected();
                    playSFX(7);
                }
            }

        }
        if (vader.getLives() == 0) {
            if(!vader.isDestroyed()) {
                vader.setIsDestroyed(true);
                ui.addScore(100 + criticalCounter);
                vaderDefeated = true;
            }
        }
    }

    public synchronized  void hitEye() {
        for (MFLasers lasers : mfLasers) {
            if (CollisionChecker.collides(eye.getHitbox(), lasers.getHitbox())) {
                lasers.setIsDestroyed(true);
                playSFX(3);
                eye.reduceLives();
                if(lasers.isCriticalShot()) {
                    eye.reduceLives();
                    criticalCounter++;
                }
            }
        }
        for(ProtonTorpedo torpedo : millenniumPTs) {
            if(CollisionChecker.collides(torpedo.getExHitbox(), eye.getHitbox())){
                if(!torpedo.isOnHit()) {
                    eye.reduceLives();
                    torpedo.setOnHit(true);
                }
            }
            if(CollisionChecker.collides(eye.getHitbox(), torpedo.getTHitbox())) {
                if(!torpedo.isOnHit()) {
                    torpedo.setOnContact(true);
                    playSFX(9);
                    eye.reduceLives();
                    eye.reduceLives();
                    eye.reduceLives();
                    eye.reduceLives();
                    eye.reduceLives();
                    torpedo.setOnHit(true);
                }
            }
        }
        if (eye.getLives() == 0) {
            if(!eye.isDestroyed()) {
                eye.setIsDestroyed(true);
                ui.addScore(100 + criticalCounter);
                dsDefeated = true;
            }
        }
    }

    public void hitCollectible() {
        for (MFLasers lasers : mfLasers) {
            for(Collectibles collectible : collectibles) {
                if (CollisionChecker.collides(collectible.getHitbox(), lasers.getHitbox())) {
                    collectible.setIsHit(true);
                    if (collectible instanceof HyperCoolant<?>) {
                        sShip.resetCoolerCounter();
                        sShip.setHyperCooled(true);
                        playSFX(18);
                    }
                    if (collectible instanceof MajorHyperMatterBooster<?>) {
                        sShip.majorBoost();
                        playSFX(16);
                    }
                    if (collectible instanceof MinorHyperMatterBooster<?>) {
                        sShip.minorBoost();
                        playSFX(16);
                    }
                    if (collectible instanceof ProtonTorpedoCharge<?>) {
                        sShip.addTorpedoCharge();
                        playSFX(16);
                    }
                    if (collectible instanceof ShieldGenerator<?>) {
                        mShield.resetLives();
                        sShip.setDeflectorShield(true);
                        playSFX(17);
                    }
                    lasers.setIsDestroyed(true);
                }
            }
        }
    }

    public synchronized void vShieldBounce() {

        for (MFLasers lasers : mfLasers) {
            if (CollisionChecker.collides(vShield.getHitbox(), lasers.getHitbox())) {
                if(!lasers.isDeflected()) {
                    lasers.deflected();
                    playSFX(7);
                    vShield.reduceLives();
                }
            }
        }
    }

    private void shieldManager() {
        if(mShield.isDestroyed()) {
            sShip.setDeflectorShield(false);
            mShield.resetLives();
        }
        if(sShip.isDeflectorShield()) {
            mShield.surround();
        }
    }

     public synchronized void hitTorpedo() {
         for (MFLasers lasers : mfLasers) {
            for (ProtonTorpedo torpedo : vaderPTs) {
                if(CollisionChecker.collides(lasers.getHitbox(), torpedo.getTHitbox())) {
                    lasers.setIsDestroyed(true);
                    torpedo.setOnContact(true);
                    dropsTorpedoes(torpedo);
                    if(!torpedo.isOnHit()) {
                        playSFX(9);
                        torpedo.setOnHit(true);
                    }
                }
            }
        }
         for (ProtonTorpedo torpedo : millenniumPTs) {
             for (ProtonTorpedo vTorpedo : vaderPTs) {
                 if(CollisionChecker.collides(torpedo.getTHitbox(), vTorpedo.getTHitbox())) {
                     torpedo.setIsDestroyed(true);
                     torpedo.setOnContact(true);
                     dropsTorpedoes(vTorpedo);
                     if(!torpedo.isOnHit()) {
                         playSFX(9);
                         torpedo.setOnHit(true);
                     }
                 }
             }
         }
     }


    public synchronized void moveEnemyBullets() {

        for (EnemyLaser enemyLaser : enemyLasers) {
            enemyLaser.move();
        }
    }

    public synchronized void moveVLasers() {

        for (VaderLasers vaderLaser : vaderLasers) {
            vaderLaser.move();
        }
    }

    public synchronized void moveVTorpedoes() {

        for (ProtonTorpedo torpedo : vaderPTs) {
            torpedo.move();
            if(torpedo.isOnContact()) {
                if(!torpedo.isOnHit()) {
                    playSFX(9);
                    torpedo.setOnHit(true);
                }
            }
        }
    }

    public synchronized void moveSTorpedoes() {

        for (ProtonTorpedo torpedo : millenniumPTs) {
            torpedo.move();
            if(torpedo.isOnContact()) {
                if(!torpedo.isOnHit()) {
                    playSFX(9);
                    torpedo.setOnHit(true);
                }
            }
        }
    }


    public void spawnSBullets() {
        if (!sShip.isOverHeated() && sShip.isShooting() && !sShip.isTorpedoesEngaged()){
            if(frameCounter % 10 == 0) {
                mfLasers.add(new MFLasers(sShip.getPosY(), sShip.getPosX(), isCriticalShot(), this));
                sShip.cannonHeatToll();
                playSFX(2);
            }
        }
    }
    public void spawnSTorpedoes() {
        tCooldowCounter--;
        if(tCooldowCounter < 0) {
            tCooldowCounter = 0;
        }
        if (sShip.isTorpedoesEngaged() && sShip.getTorpedoCharges() > 0 && sShip.isShooting() && tCooldowCounter == 0){
            millenniumPTs.add(new ProtonTorpedo(sShip, Operations.centerRect(sShip.getPosY(), sShip.getMaxPosY()) - 15 , sShip.getMaxPosX()));
            playSFX(1);
            sShip.shootTorpedo();
            tCooldowCounter = 50;
        }
    }
    public int availableTorpedoLauncher() {
        int availableLauncher = 0;

        if(switchCounter > 1) {
            switchCounter = 0;
        }
        switch (switchCounter) {
            case 0 -> availableLauncher = vader.getPosY() + 13;
            case 1 -> availableLauncher = vader.getPosY() + 78;
        }
        switchCounter++;
        return availableLauncher;
    }
    public synchronized void spawnEBullets() {
        if(frameCounter % 240 == 0) {
            for (EnemyFighter enemyFighter : fighters) {
                enemyLasers.add(new EnemyLaser(enemyFighter.getPosY(), enemyFighter.getPosX() , this));
                playSFX(0);
            }
        }
    }


    public synchronized void spawnEFighters() {
        if(gamePhase == beginPhase1) {
            if (enemyCounter <= 7) {
                if (frameCounter == 60) {
                    fighters.add(new EnemyFighter(this));
                    enemyCounter++;
                }
            }
            if(enemyCounter>7 && enemyCounter<= 45) {
                if (frameCounter == 100 || frameCounter == 120  ) {
                    fighters.add(new EnemyFighter(this));
                    enemyCounter++;
                }
            }
            if(enemyCounter>45 && enemyCounter<= 50) {
                if (frameCounter  == 200 || frameCounter == 240 || frameCounter == 260) {
                    fighters.add(new EnemyFighter(this));
                    enemyCounter++;
                }
            }
            if(enemyCounter>50 && enemyCounter<= 55) {
                if (frameCounter%150 == 0) {
                    fighters.add(new EnemyFighter(this));
                    enemyCounter++;
                }
            }
            if(enemyCounter>55 && enemyCounter<= 70) {
                if (frameCounter%100 == 0) {
                    fighters.add(new EnemyFighter(this));
                    enemyCounter++;
                }
            }
        }
        if(gamePhase == beginPhase3) {
            if (!dsDefeated) {
                if (frameCounter % 90 == 0) {
                    fighters.add(new EnemyFighter(this));
                }
            }
        }
    }

    public void dropsFighter(EnemyFighter enemy) {
        int i = (int) Math.ceil(Math.random() * 100);
        if(i <= 15) {
            collectibles.add(new MinorHyperMatterBooster<>(enemy));
        }
        if(i > 15 && i <= 25) {
            collectibles.add(new HyperCoolant<>(enemy));
        }
        if(i > 25 && i <= 30) {
            collectibles.add(new MajorHyperMatterBooster<>(enemy));
        }
        if(i > 30 && i <= 32) {
            collectibles.add(new ProtonTorpedoCharge<>(enemy));
        }
        if(i > 32 && i <= 35) {
            collectibles.add(new ShieldGenerator<>(enemy));
        }
    }

    public void dropsTorpedoes(ProtonTorpedo torpedo) {
        int i = (int) Math.ceil(Math.random() * 100);
        if(i <= 30) {
            collectibles.add(new ProtonTorpedoCharge<>(torpedo));
        }
        if(i > 30 && i <= 35) {
            collectibles.add(new MinorHyperMatterBooster<>(torpedo));
        }
        if(i > 35 && i <= 38) {
            collectibles.add(new HyperCoolant<>(torpedo));
        }
    }


    private void ReadySuperLaser() {
        sLaser.sLaserCharge();

        if(sLaser.isIgnite()) {
            toggleCounter++;
            if(toggleCounter > 120) {
                sShip.OHKO();
            }
        }
    }

    public void drawEFighters(Graphics2D gfx) {
        for(EnemyFighter enemyFighter : fighters) {
            enemyFighter.summonFighter(gfx);
        }
    }

    public void drawEBullets (Graphics2D gfx) {
        for(EnemyLaser enemyLaser : enemyLasers) {
            enemyLaser.draw(gfx);
        }
    }

    public void drawBullets (Graphics2D gfx) {
        for(MFLasers lasers : mfLasers) {
            lasers.draw(gfx);
        }
    }

    public void drawVLasers(Graphics2D gfx) {
        for(VaderLasers lasers : vaderLasers) {
            lasers.draw(gfx);
        }
    }

    public void drawVTorpedoes(Graphics2D gfx) {
        for(ProtonTorpedo torpedo : vaderPTs) {
            torpedo.draw(gfx);
        }
    }

    public void drawSTorpedoes(Graphics2D gfx) {
        for(ProtonTorpedo torpedo : millenniumPTs) {
            torpedo.draw(gfx);
        }
    }

    public void drawCollectibles(Graphics2D gfx) {
        for(Collectibles collectible : collectibles) {
            collectible.animation(gfx);
            collectible.behaviour();
            collectible.draw(gfx);
        }
    }

    public void phaseChecker(){
        if(gamePhase == preparePhase1) {
            if(switchCounter == 0) {
                switchCounter++;
                startMusic = true;
            }
            if(finishCS) {
                finishCS = false;
                toggleCounter = 0;
                switchCounter = 0;
                gameState = playState;
                gamePhase = beginPhase1;
            }
        }
        if(gamePhase == beginPhase1) {

            if (enemyCounter >= 7 && fighters.size() == 0 && enemyLasers.size() == 0) {

                if (toggleCounter >= (3 * 60)) {
                    startMusic = true;
                    toggleCounter = 0;
                    enemyCounter = 0;
                    frameCounter = 0;
                    cs.resetCSCounter();
                    gameState = cinematicState;
                    gamePhase = preparePhase2;
                } else {
                    toggleCounter++;
                }
            }

        }if(gamePhase == preparePhase2) {
            if(canMoveDs) {
                if(switchCounter == 0) {
                    switchCounter++;
                    startMusic = true;
                }
            }
            if(finishCS) {
                finishCS = false;
                toggleCounter = 0;
                frameCounter = 0;
                enemyCounter = 0;
                switchCounter = 0;
                resetCollectibles();
                defaultMovement();
                gameState = playState;
                gamePhase = beginPhase2;
            }
        }

        if(gamePhase == beginPhase2) {
            if(vaderDefeated && vaderPTs.size() == 0 && vaderLasers.size() == 0) {
                if (toggleCounter == (60)) {
                    toggleCounter = 0;
                    frameCounter = 0;
                    enemyCounter = 0;
                    criticalCounter = 0;
                    cs.resetCSCounter();
                    gameState = cinematicState;
                    gamePhase = preparePhase3;
                } else {
                toggleCounter++;
                }
            }
        }

        if(gamePhase == preparePhase3) {
            if(finishCS) {
                finishCS = false;
                toggleCounter = 0;
                frameCounter = 0;
                enemyCounter = 0;
                switchCounter = 0;
                resetCollectibles();
                defaultMovement();
                gameState = playState;
                gamePhase = beginPhase3;
            }
        }
    }

    public void musicController() {

        switch (gamePhase) {
            case preparePhase1 -> playMusicLoop(5);
            case preparePhase2 -> stopMusic();
            case beginPhase2 -> playMusicLoop(4);
            case preparePhase3 -> stopMusic();
            case beginPhase3 -> playMusicLoop(12);
        }

    }
    public void playerMovementController() {
        if (movingUp) {
            sShip.moveSShipUp();
        }
        if (movingDown) {
            sShip.moveSShipDown();
        }
        if (movingLeft) {
            sShip.moveSShipLeft();
        }
        if (movingRight) {
            sShip.moveSShipRight();
        }
        if (movingUpReset) {
            if(!isMFMoving()) {
                sShip.moveUpReset();
            } else {
                movingUpReset = false;
            }
        }
        if (movingDownReset) {
            if(!isMFMoving()) {
                sShip.moveDownReset();
            } else {
                movingDownReset = false;
            }
        }
        if (movingLeftReset) {
            if(!isMFMoving()) {
                sShip.moveLeftReset();
            } else {
                movingLeftReset = false;
            }
        }
        if (movingRightReset) {
            if(!isMFMoving()) {
                sShip.moveRightReset();
            } else {
                movingRightReset = false;
            }
        }
    }

    private void resetCollectibles() {
        collectibles = new LinkedList<>();
    }
    private void defaultMovement() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        movingUpReset = false;
        movingDownReset = false;
        movingLeftReset = false;
        movingRightReset = false;
    }

    public boolean isMFMoving() {
        if(movingUp || movingDown || movingLeft || movingRight) {
            return true;
        }
        return false;
    }

    public boolean isCriticalShot() {
        return sShip.getCannonHeat() == 0;
    }

    public void setDefaultVader() {
        standardPattern = true;
        laserFlurry = false;
        vDeflectorShield = false;
        forceStun = false;
    }

    private void overheatSFXs() {
        if(sShip.isOverHeated()) {
            overheatCounter++;
            if(overheatCounter > 121) {
                overheatCounter = 1;
            }
            if(overheatCounter == 1) {
                playSFX(11);
                playSFX(10);
            }
        }
    }
    private void torpedoEngagedSFX() {
        if(noChargesSFX) {
            playSFX(15);
            noChargesSFX = false;
        }
        if(sShip.isTorpedoesEngaged()) {
            if(torpedoSFX) {
                playSFX(13);
                torpedoSFX = false;
            }
        } if(!sShip.isTorpedoesEngaged()) {
            if(!torpedoSFX) {
                playSFX(15);
                torpedoSFX = true;
            }
        }
    }

    private void sfxController() {
        overheatSFXs();
        torpedoEngagedSFX();
    }
    public void playMusicLoop (int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic () {
        sound.stop();
    }

    public void playSFX (int i) {sound.setFile(i);
        sound.play();
    }

    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    public int getCellSize() {
        return scaledCellSize;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingUpReset(boolean movingUpReset) {
        this.movingUpReset = movingUpReset;
    }

    public void setMovingDownReset(boolean movingDownReset) {
        this.movingDownReset = movingDownReset;
    }

    public void setMovingLeftReset(boolean movingLeftReset) {
        this.movingLeftReset = movingLeftReset;
    }

    public void setMovingRightReset(boolean movingRightReset) {
        this.movingRightReset = movingRightReset;
    }

    public void setTorpedoSFX(boolean torpedoSFX) {
        this.torpedoSFX = torpedoSFX;
    }

    public void setNoChargesSFX(boolean charges) {
        noChargesSFX = charges;
    }
}
