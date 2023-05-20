package ENTITY;

import MAIN.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static UTILS.Constants.Directions.LEFT;
import static UTILS.Constants.Directions.RIGHT;
import static UTILS.Constants.enemyConstants.*;

public class Ghost extends Enemy{
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private int attackBoxOffsetY;
    public Ghost(float x, float y) {
        super(x, y, GHOST_WIDTH, GHOST_HEIGHT, GHOST);
        aniSpeed=19;
        walkDir=LEFT;
        initHitBox(30,20);
        initAttackBox();
    }
    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(80* Game.SCALE),(int)(25*Game.SCALE));
        attackBoxOffsetX=(int)(12.5*Game.SCALE);
        attackBoxOffsetY=(int)(1*Game.SCALE);
    }

    public void update(int[][]lvlData,Hero hero){
        updateBehavior(lvlData,hero);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateBehavior(int[][] lvlData, Hero hero) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (state) {
                case IDLE:
                    newState(MOVE);
                    break;
                case MOVE:
                    if(canSeePlayer(lvlData,hero)) {
                        turnToPlayer(hero);
                        if (isPlayerInAttackRange(hero))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex==0)
                        attackCheck=false;

                    if(aniIndex==5 && !attackCheck)
                        checkPlayerHit(hero,attackBox);

                    break;
                case TAKE_DAMAGE:
                    break;
            }
        }
    }

    private void updateAttackBox() {
        attackBox.x= (hitBox.x - attackBoxOffsetX);
        attackBox.y= hitBox.y -attackBoxOffsetY;
    }

    public int flipX(){
        if(walkDir==RIGHT)
            return GHOST_WIDTH;
        return 0;
    }

    public int flipW(){
        if(walkDir==RIGHT)
            return -1;
        return 1;
    }

    public void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.BLUE);
        g.drawRect((int)(attackBox.x-xLvlOffset), (int)(attackBox.y-yLvlOffset), (int)attackBox.width,(int) attackBox.height);
    }
}
