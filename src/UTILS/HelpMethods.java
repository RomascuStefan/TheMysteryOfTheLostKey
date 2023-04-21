package UTILS;

import MAIN.Game;

import java.awt.geom.Rectangle2D;

import static UTILS.Constants.Directions.LEFT;

public class HelpMethods {
    public static boolean canMoveHere(float x,float y,float width,float height,int[][]lvlData){
        if(!isSolid(x,y,lvlData))
            if(!isSolid(x+width,y+height,lvlData))
                if(!isSolid(x+width,y,lvlData))
                    if(!isSolid(x,y+height,lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x,float y,int[][]lvlData){
        int maxWidth= lvlData[0].length * Game.TILE_SIZE;
        if(x<0||x>= maxWidth)
            return true;
        if(y<0||y>= Game.GAME_HEIGHT)
            return true;

        float xIndex=x/ Game.TILE_SIZE;
        float yIndex=y/ Game.TILE_SIZE;

        return isTileSolid((int)xIndex,(int)yIndex,lvlData);
    }

    public static float getEntityXposNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile=(int)(hitbox.x/ Game.TILE_SIZE);
        if(xSpeed>0){
            int tileXPos=currentTile* Game.TILE_SIZE;
            int xOffset=(int)(Game.TILE_SIZE-hitbox.width);
            return tileXPos+xOffset-1;
        }
        else{
            return currentTile* Game.TILE_SIZE;

        }
    }

    public static float getEntityYPosUnderAbove(Rectangle2D.Float hitbox, float jumpSpeed){
        int currentTile=(int)(hitbox.y/ Game.TILE_SIZE);
        if(jumpSpeed>0){
            int tileYPos=currentTile* Game.TILE_SIZE;
            int yOffset=(int)(Game.TILE_SIZE-hitbox.height);
            return tileYPos+yOffset-1;
        }
        else{
            return currentTile* Game.TILE_SIZE;
        }

    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][]lvlData){
        if(!isSolid(hitbox.x, hitbox.y+ hitbox.height+1, lvlData))
            if(!isSolid(hitbox.x+ hitbox.width, hitbox.y+ hitbox.height+1, lvlData))
                return  false;

        return  true;

    }

    public  static  boolean isFloor(Rectangle2D.Float hitbox,float xSpeed, int[][]lvlData,int dir){
        if (dir==LEFT)
            return isSolid(hitbox.x -xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return isSolid(hitbox.x +xSpeed+ hitbox.width*Game.SCALE, hitbox.y + hitbox.height + 1, lvlData);

    }

    public static boolean isTileSolid(int xTile,int yTile,int[][]lvlData){
        int value=lvlData[yTile][xTile];
        if(value <=144)
            return  true;
        return  false;
    }

    public static boolean isAllTileWalkable(int xStart,int xEnd,int y,int[][]lvlData){
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isSolid(xStart + i, y, lvlData))
                return false;
            if (!isSolid(xStart + i, y+1, lvlData))
                return false;

            if (!isSolid(xStart + i, y-1, lvlData))
                return false;

        }
        return true;
    }

    public static boolean heroInSight(int[][]lvlData,Rectangle2D.Float hitBox1,Rectangle2D.Float hitBox2,int tileY){
        int xTile1= (int) (hitBox1.x/Game.TILE_SIZE);
        int xTile2= (int) (hitBox2.x/Game.TILE_SIZE);

        if(xTile1<xTile2)
            return isAllTileWalkable(xTile2,xTile1,tileY,lvlData);

        else
            return isAllTileWalkable(xTile1,xTile2,tileY,lvlData);
    }
}
