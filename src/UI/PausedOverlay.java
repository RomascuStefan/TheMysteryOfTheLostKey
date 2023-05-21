package UI;

import GAMESTATES.Gamestate;
import GAMESTATES.Playing;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.pausedButtons.*;
import static UTILS.Constants.UI.urmButtons.*;

public class PausedOverlay {
    private BufferedImage backgroundImg;
    private Playing playing;
    private int bgX,bgY,bgWidth,bgHeight;
    private SoundButton musicButton,sfxButton;
    private UrmButton menuB,unpauseB;


    public PausedOverlay(Playing playing){
        this.playing=playing;
        loadBackground();
        createSoundBtns();
        createUrmBtns();
    }

    private void createUrmBtns() {
        int menuY=(int)(260*Game.SCALE);
        int menuX=(int)(460*Game.SCALE);
        int unpauseX=(int)(512*Game.SCALE);

        menuB=new UrmButton(menuX,menuY,URM_SIZE,URM_SIZE,2);
        unpauseB=new UrmButton(unpauseX,menuY,URM_SIZE,URM_SIZE,0);
    }

    private void createSoundBtns() {
        int soundY=(int)(260*Game.SCALE);
        int musicX=(int)(233*Game.SCALE);
        int sfxX=(int)(285*Game.SCALE);
        musicButton=new SoundButton(musicX,soundY,SOUND_SIZE,SOUND_SIZE);
        sfxButton=new SoundButton(sfxX,soundY,SOUND_SIZE,SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImg= LoadSave.getSpriteAtlas(LoadSave.PAUSED_BACKGROUND);
        bgWidth= (int) (backgroundImg.getWidth()* Game.SCALE);
        bgHeight= (int) (backgroundImg.getHeight()* Game.SCALE);
        bgX=Game.GAME_WIDTH/2-bgWidth/2;
        bgY=100;
    }

    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        unpauseB.update();
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg,bgX,bgY,bgWidth,bgHeight,null);

        musicButton.draw(g);
        sfxButton.draw(g);

        menuB.draw(g);
        unpauseB.draw(g);
    }



    public void mousePressed(MouseEvent e) {
        if(isInside(e,musicButton))
            musicButton.setMousePressed(true);
        else if(isInside(e,sfxButton))
            sfxButton.setMousePressed(true);
        else if(isInside(e,menuB))
            menuB.setMousePressed(true);
        else if(isInside(e,unpauseB))
            unpauseB.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if(isInside(e,musicButton)) {
            if(musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        }
        else if(isInside(e,sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        }
        else if(isInside(e,menuB)){
            if(menuB.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
                playing.unpaseGame();
            }
        }
        else if(isInside(e,unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unpaseGame();
        }

            musicButton.resetBools();
            sfxButton.resetBools();
            menuB.resetBooleans();
            unpauseB.resetBooleans();
    }

    public void mouseMove(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if(isInside(e,musicButton))
            musicButton.setMouseOver(true);
        else if(isInside(e,sfxButton))
            sfxButton.setMouseOver(true);
        else if(isInside(e,menuB))
            menuB.setMouseOver(true);
        else if(isInside(e,unpauseB))
            unpauseB.setMouseOver(true);
    }



    private boolean isInside(MouseEvent e,PausedButton b){
        return b.getBounds().contains(e.getX(),e.getY());

    }


}
