package ENTITY;
import MAIN.Game;
import UTILS.HelpMethods;

import java.awt.geom.Rectangle2D;

import static UTILS.Constants.enemyConstants.*;
import static UTILS.HelpMethods.*;
import static UTILS.Constants.Directions.*;

public abstract class Enemy extends Entity{

    protected int aniIndex,enemyState,enemyType;
    protected int aniTick,aniSpeed=25;
    protected boolean firstUpdate=true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity=0.04f* Game.SCALE;
    protected float walkSpeed=0.25f * Game.SCALE;
    protected int walkDir=RIGHT;
    protected int tileY;
    protected float attackRange=1.75f*Game.TILE_SIZE;
    protected int maxHealt;
    protected int currentHealth;
    protected boolean active=true;
    protected boolean attackCheck;



    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType=enemyType;
        initHitBox(x,y,width,height);
        maxHealt=getMaxHealth(enemyType);
        currentHealth=maxHealt;
    }



    protected void firstUpdateCheck(int[][]lvlData){
        if (!isEntityOnFloor(hitBox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][]lvlData){
        if (canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y = getEntityYPosUnderAbove(hitBox, fallSpeed);
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

    protected boolean canSeePlayer(int[][]lvlData,Hero hero){
        int heroTileY= (int) (hero.getHitBox().y/Game.TILE_SIZE);
        if(heroTileY==tileY)
            if(isPlayerInRange(hero)) {
                if (heroInSight(lvlData,hitBox,hero.hitBox,tileY))
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
        return absValue<=(attackRange*5);
    }

    protected void newState(int enemyState){
        this.enemyState=enemyState;
        aniTick=0;
        aniIndex=0;
    }

    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(enemyType,enemyState)){
                aniIndex=0;
                switch (enemyState){
                    case ATTACK,TAKE_DAMAGE -> enemyState=IDLE;
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



    public int getAniIndex(){
        return  aniIndex;
    }

    public int getEnemyState(){
        return  enemyState;
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
            hero.changeHealth(-getEnemyDamage(GOBLIN));
        attackCheck=true;
    }

    public void resetEnemy() {
        hitBox.x=x;
        hitBox.y=y;
        firstUpdate=true;
        currentHealth=maxHealt;
        newState(IDLE);
        active=true;
        fallSpeed=0;
    }
}
