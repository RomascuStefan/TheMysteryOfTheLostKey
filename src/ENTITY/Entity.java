package ENTITY;


import MAIN.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;



public abstract class Entity {

    protected float x,y;
    protected int width,height;
    protected Rectangle2D.Float hitBox;
    protected int aniTick,aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir=false;
    protected int maxHealth;
    protected int currentHealth;
    protected float walkSpeed;

    public Entity(float x,float y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

    }

    protected void initHitBox(float width,float height) {
        hitBox=new Rectangle2D.Float(x,y,(int)(width*Game.SCALE),(int)(height*Game.SCALE));
    }

    protected void updateHitBox(){
//        hitBox.x=(int)x;
//        hitBox.y=(int)y;
    }

    public Rectangle2D.Float getHitBox(){
        return  hitBox;
    }

    protected void drawHitBox(Graphics g,int xLvlOffset,int yLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(hitBox.x-xLvlOffset), (int)(hitBox.y-yLvlOffset), (int)hitBox.width,(int) hitBox.height);
    }

    public int getState(){
        return state;
    }

    public int getAniIndex(){
        return  aniIndex;
    }




}
