package ENTITY;

import MAIN.Game;

import java.awt.geom.Rectangle2D;

import static UTILS.Constants.Directions.LEFT;
import static UTILS.Constants.Directions.RIGHT;
import static UTILS.Constants.GRAVITY;
import static UTILS.Constants.enemyConstants.*;
import static UTILS.HelpMethods.*;

public abstract class Enemy extends Entity{

    protected int enemyType;
    protected int aniSpeed=25;
    protected boolean firstUpdate=true;


    protected int walkDir=RIGHT;
    protected int tileY;
    protected float attackRange=1.75f*Game.TILE_SIZE;

    protected boolean active=true;
    protected boolean attackCheck;



    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType=enemyType;

        maxHealth=getMaxHealth(enemyType);
        currentHealth=maxHealth;
        walkSpeed=0.25f * Game.SCALE;
    }



    protected void firstUpdateCheck(int[][]lvlData){
        if (!isEntityOnFloor(hitBox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][]lvlData){
        if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitBox.y = getEntityYPosUnderAbove(hitBox, airSpeed);
            tileY=(int)(hitBox.y/Game.TILE_SIZE);
        }
    }

    protected void move(int[][]lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
            if (isFloor(hitBox, xSpeed, lvlData,walkDir)) {
                hitBox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    protected boolean canSeePlayer(int[][] lvlData, Hero player) {
        int playerTileY = (int) (player.getHitBox().y / Game.TILE_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (heroInSight(lvlData, hitBox, player.hitBox, tileY))
                    return true;
            }

        return false;
    }

    protected void turnToPlayer(Hero hero){
        if(hero.hitBox.x>hitBox.x)
            walkDir=RIGHT;

        else
            walkDir=LEFT;

    }

    protected boolean isPlayerInAttackRange(Hero hero){
        int absValue= (int) Math.abs(hero.hitBox.x-hitBox.x);
        return absValue<=attackRange;


    }

    protected boolean isPlayerInRange(Hero hero) {
        int absValue= (int) Math.abs(hero.hitBox.x-hitBox.x);
        return absValue<=(attackRange*6);
    }

    protected void newState(int enemyState){
        this.state=enemyState;
        aniTick=0;
        aniIndex=0;
    }

    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(enemyType,state)){
                aniIndex=0;
                switch (state){
                    case ATTACK,TAKE_DAMAGE -> state=IDLE;
                    case DIE -> active=false;
                }
            }

        }
    }
    protected void changeWalkDir() {
        if(walkDir==LEFT) {
            walkDir = RIGHT;
        }
        else {
            walkDir = LEFT;
        }
    }



    public boolean isActive(){return active;}

    public void hurt(int damage){
        currentHealth-=damage;
        if(currentHealth<=0)
            newState(DIE);
        else
            newState(TAKE_DAMAGE);
    }

    protected void checkPlayerHit(Hero hero,Rectangle2D.Float attackBox) {
        if(attackBox.intersects(hero.hitBox))
            hero.changeHealth(-getEnemyDamage(enemyType));
        attackCheck=true;
    }



    public void resetEnemy() {
        hitBox.x=x;
        hitBox.y=y;
        firstUpdate=true;
        currentHealth=maxHealth;
        newState(IDLE);
        active=true;
        airSpeed=0;
    }
}
