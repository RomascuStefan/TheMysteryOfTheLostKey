package UTILS;

import ENTITY.Goblin;
import MAIN.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static UTILS.Constants.enemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS="sprite hero.png";
    public static final String LEVEL_ONE_FLOATING_PLATFORMS="Mossy - FloatingPlatforms.png";
    public static final  String LEVEL_ONE_TILE="Mossy - TileSet.png";
    public static final  String LEVEL_ONE_DECORATIONS="Mossy - Decorations_Hazards.png";
    public static final String LEVEL_ONE_HILLS = "Mossy - MossyHills.png";
    public static final String BUTTON_ATLAS = "button atlas 3.png";
    public static final String MENU_BACKGROUND = "menu background.png";
    public static final String LEVEL_ONE_BACKGROUND = "background level.png";
    public static final String GOBLIN_SPRITE = "GoblinTank Sprite.png";
    public static final String HEALTH_BAR = "no health bar.png";
    public static final String PAUSED_BACKGROUND = "gamePaused.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String COMPLETED_IMG = "level complete.png";
    public static final String SMALL_HEAL = "potion_small.png";
    public static final String BIG_HEAL = "potion_big.png";
    public static final String COMMON_CHEST ="chest common.png";
    public static final String RARE_CHEST ="chest rar.png";
    public static final String LEGENDAR_CHEST ="chest legendar.png";
    public static final String KEY ="key.png";
    public static final String HOUND_SPRITE ="houndAtlas.png";
    public static final String GHOST_SPRITE ="ghostAtlas.png";
    public static final String ARROW_SPRITE ="arrow.png";
    public static final String DEATH_SCREEN ="levelLost.png";


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

    public static BufferedImage[] getAllLvls(){
        URL url=LoadSave.class.getResource("/levelsData");
        File file=null;

        try {
            file = new File(url.toURI());
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }

        File[] files=file.listFiles();
        File[] filesSorted=new File[files.length];
//        for(File f:files)
//            System.out.println("file: "+f.getName());

        for(int i=0;i<filesSorted.length;i++)
            for(int j=0;j<files.length;j++){
                if(files[j].getName().equals(""+(i+1)+".png"))
                    filesSorted[i]=files[j];

            }

        BufferedImage[] imgs=new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;

    }






}
