package UTILS;

import ENTITY.Goblin;
import MAIN.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static UTILS.Constants.enemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS="sprite hero.png";
    public static final String LEVEL_ONE_FLOATING_PLATFORMS="Mossy - FloatingPlatforms.png";
    public static final  String LEVEL_ONE_DATA="map_one_data1.png";
    public static final  String LEVEL_ONE_TILE="Mossy - TileSet.png";
    public static final  String LEVEL_ONE_DECORATIONS="Mossy - Decorations_Hazards.png";
    public static final String LEVEL_ONE_HILLS = "Mossy - MossyHills.png";
    public static final String BUTTON_ATLAS = "buttonAtlas.png";
    public static final String MENU_BACKGROUND = "menu background.png";
    public static final String LEVEL_ONE_BACKGROUND = "background level.png";
    public static final String GOBLIN_SPRITE = "GoblinTank Sprite.png";
    public static final String HEALTH_BAR = "no health bar.png";
    public static final String PAUSED_BACKGROUND = "gamePaused.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";


    public static BufferedImage getSpriteAtlas(String fileName){
        BufferedImage img;
        InputStream is=LoadSave.class.getResourceAsStream("/"+fileName);

        try {
            img= ImageIO.read(is);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return img;
    }

    public static ArrayList<Goblin> getGoblin(){
        BufferedImage img=getSpriteAtlas(LEVEL_ONE_DATA);
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

    public static int[][]GetLevelData(){
        int air=145;

        BufferedImage img=getSpriteAtlas(LEVEL_ONE_DATA);
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


}
