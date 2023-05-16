package LEVELS;

import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level level1;



    public LevelManager(Game game){
        this.game=game;
        importOutiseSprite();
        level1=new Level(LoadSave.GetLevelData());
    }

    private void importOutiseSprite() {
        BufferedImage floatingPlatform=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_FLOATING_PLATFORMS);
        BufferedImage tileSet=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_TILE);
        BufferedImage decorations=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_DECORATIONS);
        BufferedImage hills=LoadSave.getSpriteAtlas(LoadSave.LEVEL_ONE_HILLS);

        levelSprite=new BufferedImage[146]; //[16+49+64++16+1]]
        //16-floating platforms
        //49-tiles
        //1-void (aer)
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
            for(int i=0;i< level1.getLvlData()[0].length;i++){
                int index=level1.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],i* Game.TILE_SIZE-xLvlOffset,j*Game.TILE_SIZE -yLvlOffset,Game.TILE_SIZE,Game.TILE_SIZE,null);
            }
    }

    public void update(){

    }

    public Level getCurrentLvl() {
        return level1;
    }
}
