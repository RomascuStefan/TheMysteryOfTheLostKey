package UI;

import GAMESTATES.Gamestate;
import GAMESTATES.Playing;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.urmButtons.URM_SIZE;

public class LevelCompletedOverlay {
    private  Playing playing;
    private UrmButton menu,next;
    private BufferedImage img;
    private int bgX,bgY,bgWidth,bgHeight;


    public LevelCompletedOverlay(Playing playing){
        this.playing=playing;
        initImg();
        initBtn();
    }

    private void initBtn() {
        int menuX= (int) (330*Game.SCALE);
        int nextX= (int) (400*Game.SCALE);
        int y= (int) (256*Game.SCALE);

        next=new UrmButton(nextX,y,URM_SIZE,URM_SIZE,0);
        menu=new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);


    }

    private void initImg() {
        img= LoadSave.getSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgWidth= (int) (img.getWidth()*Game.SCALE);
        bgHeight= (int) (img.getHeight()*Game.SCALE);
        bgX= Game.GAME_WIDTH/2-bgWidth/2;
        bgY= (int) (75*Game.SCALE);
    }

    public void update(){
        next.update();
        menu.update();
    }

    public void draw(Graphics g){
        g.drawImage(img,bgX,bgY,bgWidth,bgHeight,null);
        next.draw(g);
        menu.draw(g);
    }

    private boolean isIn(UrmButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }

    public void mouseMove(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu,e))
            menu.setMouseOver(true);
        else if(isIn(next,e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e)){
            if(menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state=Gamestate.MENU;
            }
        }
        else if(isIn(next,e)){
            if(next.isMousePressed()) {
                playing.loadNextLevel();
            }

        }
        menu.resetBooleans();
        next.resetBooleans();
    }

    public void mousePressed(MouseEvent e){

        if(isIn(menu,e))
            menu.setMousePressed(true);
        else if(isIn(next,e))
            next.setMousePressed(true);
    }
}
