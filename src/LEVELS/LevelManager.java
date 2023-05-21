package LEVELS;

import GAMESTATES.Gamestate;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex=0;


    public LevelManager(Game game){
        this.game=game;
        importOutiseSprite();
        levels=new ArrayList<>();
        buidAllLeves();
    }

    private void buidAllLeves() {
        BufferedImage[] allLevels=LoadSave.getAllLvls();
        for(BufferedImage img:allLevels){
            levels.add(new  Level(img));
        }
    }

    private void importOutiseSprite() {
        BufferedImage floatingPlatform=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_FLOATING_PLATFORMS);
        BufferedImage tileSet=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_TILE);
        BufferedImage decorations=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_DECORATIONS);
        BufferedImage hills=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_HILLS);

        levelSprite=new BufferedImage[146]; //[16+49+64+16+1]]
        //0-15-floating platforms
        //16-48-tiles
        //
        //
        //145-void (aer)
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                    levelSprite[4*i+j]=floatingPlatform.getSubimage(j*512,i*512,512,512);
        for(int i=0;i<7;i++)
            for(int j=0;j<7;j++)
                levelSprite[7*i+j+16]=tileSet.getSubimage(j*512,i*512,512,512);
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                levelSprite[8*i+j+64]=decorations.getSubimage(j*512,i*512,512,512);
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                levelSprite[4*i+j+129]=hills.getSubimage(j*512,i*512,512,512);


    }

    public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
        for(int j=0;j< Game.TILE_IN_HEIGHT;j++)
            for(int i=0;i< levels.get(lvlIndex).getLvlData()[0].length;i++){
                int index=levels.get(lvlIndex).getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],i* Game.TILE_SIZE-xLvlOffset,j*Game.TILE_SIZE -yLvlOffset,Game.TILE_SIZE,Game.TILE_SIZE,null);
            }
    }

    public void update(){

    }

    public Level getCurrentLvl() {
        return levels.get(lvlIndex);
    }


    public void  loadNextLevel() {
        lvlIndex++;
        if(lvlIndex>=levels.size()){
            lvlIndex=0;
            System.out.println("ai terminat jocul");
            Gamestate.state=Gamestate.MENU;
            game.save(game.getScore());
        }
        Level newLevel=levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getHero().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setMaxLvlOffset(newLevel.getMaxLvlOffsetPX(), newLevel.getMaxLvlOffsetPY());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }
}
