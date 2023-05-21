package UTILS;

import ENTITY.Ghost;
import ENTITY.Goblin;
import ENTITY.Hound;
import MAIN.Game;
import OBJECTS.Chest;
import OBJECTS.Key;
import OBJECTS.Potion;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static UTILS.Constants.Directions.LEFT;
import static UTILS.Constants.ObjectConstants.*;
import static UTILS.Constants.enemyConstants.*;

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
        if (xSpeed>0)
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
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

    public static boolean heroInSight(int[][]lvlData,Rectangle2D.Float enemyBox,Rectangle2D.Float heroBox,int tileY){
        int firstXTile=(int)(enemyBox.x/Game.TILE_SIZE);
        int secondXTile;

        if(isSolid(heroBox.x,heroBox.y+heroBox.height+1,lvlData))
            secondXTile= (int) (heroBox.x/Game.TILE_SIZE);
        else
            secondXTile= (int) ((heroBox.x+heroBox.width)/Game.TILE_SIZE);

        if(firstXTile>secondXTile)
            return isAllTileWalkable(secondXTile,firstXTile,tileY,lvlData);
        else
            return isAllTileWalkable(firstXTile,secondXTile,tileY,lvlData);
    }

    public static int[][]GetLevelData(BufferedImage img){
        int air=145;
        int[][]LevelData=new int[img.getHeight()][img.getWidth()];

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getRed();
                if(value>=air)
                    value=air;
                LevelData[j][i]=value;

            }

        return  LevelData;
    }

    public static ArrayList<Goblin> getGoblin(BufferedImage img){
        ArrayList<Goblin> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==GOBLIN)
                    list.add(new Goblin( i*Game.TILE_SIZE,j*Game.TILE_SIZE));

            }
        return list;
    }

    public static ArrayList<Hound> getHound(BufferedImage img){
        ArrayList<Hound> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==HOUND)
                    list.add(new Hound( i*Game.TILE_SIZE,j*Game.TILE_SIZE));

            }
        return list;
    }

    public static ArrayList<Ghost> getGhost(BufferedImage img){
        ArrayList<Ghost> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value==GHOST)
                    list.add(new Ghost( i*Game.TILE_SIZE,(j+1)*Game.TILE_SIZE));

            }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILE_SIZE, j * Game.TILE_SIZE);


            }
        return new Point(1*Game.TILE_SIZE,1*Game.TILE_SIZE);
    }

    public static ArrayList<Potion> getPotions(BufferedImage img){
        ArrayList<Potion> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getBlue();
                if(value==100)
                    list.add(new Potion(i*Game.TILE_SIZE,j*Game.TILE_SIZE,SMALL_POTION));
                else if(value==101)
                    list.add(new Potion(i*Game.TILE_SIZE,j*Game.TILE_SIZE,BIG_POTION));

            }
        return list;
    }

    public static ArrayList<Chest> getChest(BufferedImage img){
        ArrayList<Chest> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getBlue();
                if(value==102)
                    list.add(new Chest(i*Game.TILE_SIZE,j*Game.TILE_SIZE,COMMON_CHEST));
                else if(value==103)
                    list.add(new Chest(i*Game.TILE_SIZE,j*Game.TILE_SIZE,RARE_CHEST));
                else if(value==104)
                    list.add(new Chest(i*Game.TILE_SIZE,j*Game.TILE_SIZE,LEGENDAR_CHEST));
            }
        return list;
    }

    public static ArrayList<Key> getKey(BufferedImage img) {
        ArrayList<Key> list=new ArrayList<>();
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getBlue();
                if(value==105)
                    list.add(new Key(i*Game.TILE_SIZE,j*Game.TILE_SIZE));
            }
        return list;
    }
}
