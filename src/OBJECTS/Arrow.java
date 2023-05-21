package OBJECTS;

import java.awt.geom.Point2D;

public class Arrow{
   private Point2D.Float pos;
   private int orientation;
   private float xSpeed,ySpeed;
   private boolean active=false;

   public Arrow(float x,float y,float xSpeed,float ySpeed,int orientation){
       pos=new Point2D.Float(x,y);
       this.orientation=orientation;
       this.xSpeed=xSpeed;
       this.ySpeed=ySpeed;
   }

   public void move(){
       pos.x+=xSpeed;
       pos.y+=ySpeed;
   }

   public Point2D.Float getPos(){
       return pos;
   }

   public void setPos(){
       this.pos=pos;
   }

   public int getOrientation(){
       return  orientation;
   }

}
