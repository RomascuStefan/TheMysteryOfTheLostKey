package GAMESTATES;

import ENTITY.EnemyManager;
import ENTITY.Hero;
import LEVELS.LevelManager;
import MAIN.Game;
import OBJECTS.ArrowManager;
import OBJECTS.ObjectManager;
import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PausedOverlay;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static MAIN.Game.GAME_WIDTH;

public class Playing extends State implements StateMethods{
    private Hero hero;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ArrowManager arrowManager;
    private UI.GameOverOverlay gameOverOverlay;

    private int xMouse,yMouse;


    private int xLvlOffset;
    private int leftBorder=(int)(0.3* GAME_WIDTH);
    private int rightBorder=(int)(0.5* GAME_WIDTH);
    private int maxLvlOffsetPX;

    private int yLvlOffset;
    private int upBorder=(int)(0.6*Game.GAME_HEIGHT);
    private int downBorder=(int)(0.4*Game.GAME_HEIGHT);
    private int maxLvlOffsetPY;

    private BufferedImage bg_image;

    private PausedOverlay pausedOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean gameOver=false;
    private boolean paused=false;
    private boolean lvlCompleted=false;

    private ObjectManager objectManager;
    private boolean heroDying=false;




    public Playing(Game game) {
        super(game);
        initClass();
        bg_image=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_BACKGROUND);

        calculateLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        hero.setSpawn(levelManager.getCurrentLvl().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLvl());
        objectManager.loadObjects(levelManager.getCurrentLvl());
    }

    private void  calculateLvlOffset() {
        maxLvlOffsetPX=levelManager.getCurrentLvl().getMaxLvlOffsetPX();
        maxLvlOffsetPY=levelManager.getCurrentLvl().getMaxLvlOffsetPY();
    }

    private void initClass() {
        levelManager = new LevelManager(game);
        enemyManager=new EnemyManager(this);
        objectManager=new ObjectManager(this);
        arrowManager = new ArrowManager(this);

        hero = new Hero(150*Game.SCALE,300*Game.SCALE , (int) (150 * Game.SCALE), (int) (150 * Game.SCALE),this);
        hero.loadLvlData(levelManager.getCurrentLvl().getLvlData());
        hero.setSpawn(levelManager.getCurrentLvl().getPlayerSpawn());

        gameOverOverlay=new GameOverOverlay(this);
        pausedOverlay=new PausedOverlay(this);
        levelCompletedOverlay=new LevelCompletedOverlay(this);
    }

    public Hero getHero(){
        return hero;
    }

    public void windowFocusLost() {
        hero.resetBoolean();
    }

    @Override
    public void update() {
        if(paused){
            pausedOverlay.update();
        }
        else if(lvlCompleted){
            levelCompletedOverlay.update();
        }
        else if(gameOver){
            gameOverOverlay.update();
        }
        else if(heroDying){
            hero.update();
        }
        else{
            levelManager.update();
            objectManager.update();
            hero.update();
            enemyManager.update(levelManager.getCurrentLvl().getLvlData(), hero);
            arrowManager.update();
            checkCloseBorder();
        }

    }
    @Override
    public void draw(Graphics g) {
        drawBG(g);
        objectManager.draw(g,xLvlOffset,yLvlOffset);
        levelManager.draw(g,xLvlOffset,yLvlOffset);
        hero.render(g,xLvlOffset,yLvlOffset);
        enemyManager.draw(g,xLvlOffset,yLvlOffset);
        arrowManager.draw(g,xLvlOffset,yLvlOffset);


        if(gameOver)
            gameOverOverlay.draw(g);
        else if(paused)
            pausedOverlay.draw(g);
        else if(lvlCompleted)
            levelCompletedOverlay.draw(g);

    }

    private void checkCloseBorder() {
        int heroX=(int)hero.getHitBox().x;
        int difX=heroX-xLvlOffset;

        int heroY=(int)hero.getHitBox().y;
        int difY=heroY-yLvlOffset;

        if(difX>rightBorder)
            xLvlOffset+=difX-rightBorder;
        else if(difX<leftBorder)
            xLvlOffset+=difX-leftBorder;

        if(xLvlOffset>maxLvlOffsetPX)
            xLvlOffset=maxLvlOffsetPX;
        else if(xLvlOffset<0)
            xLvlOffset=0;


        if (difY > upBorder)
            yLvlOffset += difY - upBorder;
        else if (difY < downBorder)
            yLvlOffset += difY - downBorder;

        if (yLvlOffset > maxLvlOffsetPY)
            yLvlOffset = maxLvlOffsetPY;
        else if (yLvlOffset < 0)
            yLvlOffset = 0;

    }



    private void drawBG(Graphics g) {
        int maxW=Math.min(GAME_WIDTH,bg_image.getWidth());
        int minW=Math.min(xLvlOffset,bg_image.getWidth() - GAME_WIDTH);
        BufferedImage bgImgShow=bg_image.getSubimage(minW, (int) (yLvlOffset), maxW, (int) (Game.GAME_HEIGHT));
        g.drawImage(bgImgShow,0,0,null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver && !paused)
            if (e.getButton() == MouseEvent.BUTTON1 && e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK){
//                shootEnemy();

            }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pausedOverlay.mousePressed(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mousePressed(e);

        }else
            gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pausedOverlay.mouseReleased(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);

        }else
            gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pausedOverlay.mouseMove(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseMove(e);
        }else
            gameOverOverlay.mouseMove(e);
        xMouse=e.getX();
        yMouse=e.getY();
    }

    public void unpaseGame(){
        paused=false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {

                case KeyEvent.VK_W:
                    hero.setUp(true);
                    break;

                case KeyEvent.VK_A:
                    hero.setLeft(true);
                    break;

                case KeyEvent.VK_S:

                    break;

                case KeyEvent.VK_D:
                    hero.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    hero.setUp(true);
                    break;
                case KeyEvent.VK_H:
                    //heal
                    break;
                case KeyEvent.VK_TAB:
                    //change heal
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused=!paused;
                    break;
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch (e.getKeyCode()) {

                case KeyEvent.VK_W:
                    hero.setUp(false);
                    break;

                case KeyEvent.VK_A:
                    hero.setLeft(false);
                    break;

                case KeyEvent.VK_S:
                    //down through object
                    break;

                case KeyEvent.VK_D:
                    hero.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    hero.setUp(false);
                    break;
                case KeyEvent.VK_H:
                    //heal
                    break;
                case KeyEvent.VK_TAB:
                    //change heal
                    break;
            }

    }

    public void shootEnemy(){
        arrowManager.newArrow(hero,xMouse,yMouse);
    }

    public void resetAll() {
        gameOver=false;
        lvlCompleted=false;
        heroDying=false;
        hero.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver=gameOver;
    }

    public EnemyManager getEnemyManager(){
        return enemyManager;
    }

    public void  setMaxLvlOffset(int lvlOffsetX,int lvlOffsetY){
        this.maxLvlOffsetPY=lvlOffsetY;
        this.maxLvlOffsetPX=lvlOffsetX;
    }

    public void setLevelCompleted(boolean lvlCompleted) {
        this.lvlCompleted=lvlCompleted;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void checkPotionTouched(Rectangle2D.Float hitBox) {
        objectManager.checkObjectTouch(hitBox);
    }

    public void checkChestTouched(Rectangle2D.Float hitBox) {
        objectManager.checkChestOpen(hitBox);
    }


    public void setHeroDying(boolean heroDying) {
        this.heroDying=heroDying;
    }
}
