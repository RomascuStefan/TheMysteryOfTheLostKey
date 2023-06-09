package ENTITY;

import MAIN.Game;

import java.awt.geom.Rectangle2D;

import static UTILS.Constants.Directions.RIGHT;
import static UTILS.Constants.enemyConstants.*;

public class Goblin extends Enemy{

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private int attackBoxOffsetY;
    

    public Goblin(float x, float y) {
        super(x, y, GOBLIN_WIDTH, GOBLIN_HEIGHT, GOBLIN);
        aniSpeed=15;
        initHitBox((int)((1/10.0f)*250),(int)((1/15.0f)*420));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(135* Game.SCALE),(int)(60*Game.SCALE));
        attackBoxOffsetX=(int)(60*Game.SCALE);
        attackBoxOffsetY=(int)(30*Game.SCALE);
    }

    public void update(int[][]lvlData,Hero hero){
        updateBehavior(lvlData,hero);
        updateAnimationTick();
        updateAttackBox();

    }

    private void updateAttackBox() {
        attackBox.x= hitBox.x - attackBoxOffsetX;
        attackBox.y= hitBox.y -attackBoxOffsetY;
    }

    private void updateBehavior(int[][] lvlData,Hero hero) {
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



    public int flipX(){
        if(walkDir==RIGHT)
            return 0;
        return GOBLIN_WIDTH;
    }

    public int flipW(){
        if(walkDir==RIGHT)
            return 1;
        return -1;
    }
}
