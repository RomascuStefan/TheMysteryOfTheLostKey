package LEVELS;

import ENTITY.Ghost;
import ENTITY.Goblin;
import ENTITY.Hound;
import MAIN.Game;
import OBJECTS.Chest;
import OBJECTS.Key;
import OBJECTS.Potion;
import UTILS.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static UTILS.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private int [][]lvlData;
    private ArrayList<Hound> hounds;
    private ArrayList<Goblin> goblins;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Potion> potions;
    private ArrayList<Chest> chests;
    private ArrayList<Key> keys;
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
        createKeys();
        calculateLvlOffset();
        calculatePlayerSpawn();
    }

    private void createChests() {
        chests= HelpMethods.getChest(img);
    }

    private void createPotions() {
        potions=HelpMethods.getPotions(img);
    }
    private void createKeys(){
        keys=HelpMethods.getKey(img);
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
        hounds=getHound(img);
        ghosts=getGhost(img);
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
    public ArrayList<Hound> getHounds(){
        return  hounds;
    }
    public ArrayList<Chest> getChests() {
        return chests;
    }
    public ArrayList<Potion> getPotions() {
        return potions;
    }
    public ArrayList<Key> getKey() {
        return keys;
    }

    public ArrayList<Ghost> getGhosts() {return ghosts;
    }
}
