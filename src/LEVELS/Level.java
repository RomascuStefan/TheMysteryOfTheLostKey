package LEVELS;

import ENTITY.Goblin;
import MAIN.Game;
import OBJECTS.Chest;
import OBJECTS.Potion;
import UTILS.HelpMethods;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static UTILS.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private int [][]lvlData;
    private ArrayList<Goblin> goblins;
    private ArrayList<Potion> potions;
    private ArrayList<Chest> chests;
    private int lvlTilesWide;
    private int maxTilesOffsetX;
    private int maxLvlOffsetPX;
    private int lvlTilesTall;
    private int maxTilesOffsetY;
    private int maxLvlOffsetPY;
    private Point lvlSpawn;

    public Level(BufferedImage img){
        this.img=img;
        createLvlData();
        createEnemy();
        createPotions();
        createChests();
        calculateLvlOffset();
        calculatePlayerSpawn();
    }

    private void createChests() {
        chests= HelpMethods.getChest(img);
    }

    private void createPotions() {
        potions=HelpMethods.getPotions(img);
    }

    private void calculatePlayerSpawn() {
        lvlSpawn=GetPlayerSpawn(img);
    }

    private void calculateLvlOffset() {
        lvlTilesWide=img.getWidth();
        maxTilesOffsetX=lvlTilesWide-Game.TILE_IN_WIDTH;
        maxLvlOffsetPX=Game.TILE_SIZE*maxTilesOffsetX;

        lvlTilesTall=Math.max(img.getHeight(),38);
        maxTilesOffsetY=lvlTilesTall-Game.TILE_IN_HEIGHT;
        maxLvlOffsetPY=Game.TILE_SIZE*maxTilesOffsetY;
    }

    private void createEnemy() {
        goblins=getGoblin(img);
    }

    private void createLvlData() {
        lvlData=GetLevelData(img);
    }

    public int getSpriteIndex(int x,int y){
        return lvlData[y][x];
    }

    public  int[][]getLvlData(){
        return  lvlData;
    }
    public int getMaxLvlOffsetPX(){
        return maxLvlOffsetPX;
    }
    public int getMaxLvlOffsetPY(){
        return maxLvlOffsetPY;
    }
    public Point getPlayerSpawn(){
        return lvlSpawn;
    }
    public ArrayList<Goblin> getGoblins(){
        return  goblins;
    }
    public ArrayList<Chest> getChests() {
        return chests;
    }
    public ArrayList<Potion> getPotions() {
        return potions;
    }
}
