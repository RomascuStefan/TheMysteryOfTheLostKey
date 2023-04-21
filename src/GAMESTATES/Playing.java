package GAMESTATES;

import ENTITY.EnemyManager;
import ENTITY.Hero;
import LEVELS.LevelManager;
import MAIN.Game;
import UI.GameOverOverlay;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods{
    private Hero hero;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private UI.GameOverOverlay gameOverOverlay;


    private int xLvlOffset;
    private int leftBorder=(int)(0.3*Game.GAME_WIDTH);
    private int rightBorder=(int)(0.5*Game.GAME_WIDTH);
    private int lvlTileswide= LoadSave.GetLevelData()[0].length;
    private int maxTilesOffsetX=lvlTileswide-Game.TILE_IN_WIDTH;
    private int maxLvlOffsetPX=maxTilesOffsetX * Game.TILE_SIZE;

    private int yLvlOffset;
    private int upBorder=(int)(0.6*Game.GAME_HEIGHT);
    private int downBorder=(int)(0.4*Game.GAME_HEIGHT);
    private int lvlTilesTall=38;
    private int maxTilesOffsetY=lvlTilesTall-Game.TILE_IN_HEIGHT;
    private int maxLvlOffsetPY=maxTilesOffsetY*Game.TILE_SIZE;

    private BufferedImage bg_image;

    private boolean gameOver=false;



    public Playing(Game game) {
        super(game);
        initClass();
        bg_image=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_BACKGROUND);
    }

    private void initClass() {
        levelManager = new LevelManager(game);
        enemyManager=new EnemyManager(this);
        int[][] lvl=LoadSave.GetLevelData();
        hero = new Hero(150*Game.SCALE,300*Game.SCALE , (int) (150 * Game.SCALE), (int) (150 * Game.SCALE),this);
        hero.loadLvlData(levelManager.getCurrentLvl().getLvlData());
        gameOverOverlay=new GameOverOverlay(this);
    }

    public Hero getHero(){
        return hero;
    }

    public void windowFocusLost() {
        hero.resetBoolean();
    }

    @Override
    public void update() {
        if(!gameOver) {
            levelManager.update();
            hero.update();
            enemyManager.update(levelManager.getCurrentLvl().getLvlData(), hero);
            checkCloseBorder();
        }
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(bg_image,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        levelManager.draw(g,xLvlOffset,yLvlOffset);
        hero.render(g,xLvlOffset,yLvlOffset);
        enemyManager.draw(g,xLvlOffset,yLvlOffset);

        if(gameOver)
            gameOverOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMove(MouseEvent e) {

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
                    Gamestate.state = Gamestate.MENU;
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

    public void resetAll() {
        gameOver=false;
        hero.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver=gameOver;
    }
}
