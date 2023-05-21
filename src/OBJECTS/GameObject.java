package OBJECTS;

import MAIN.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameObject {
    protected int x,y,objType;
    protected Rectangle2D.Float hitbox;
    protected boolean active=true;
    protected int xDrawOffset,yDrawOffset;


    public GameObject(int x,int y,int objType){
        this.x=x;
        this.y=y;
        this.objType=objType;


    }

    public void reset(){
        active=true;
    }

    protected void initHitbox(int width,int height){
        hitbox=new Rectangle2D.Float(x,y,(int)(width* Game.SCALE),(int)(height*Game.SCALE));
    }

    public void drawHitbox(Graphics g,int xLvlOffset,int yLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int) (hitbox.x-xLvlOffset), (int) (hitbox.y-yLvlOffset), (int) hitbox.width, (int) hitbox.height);
    }


    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


    public boolean isActive() {
        return active;
    }


    public int getxDrawOffset() {
        return xDrawOffset;
    }


    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public void setActive(boolean active){
        this.active=active;
    }

}
