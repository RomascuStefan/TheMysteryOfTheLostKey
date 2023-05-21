package UI;

import GAMESTATES.Gamestate;
import GAMESTATES.Playing;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.urmButtons.URM_SIZE;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
    private int imgX,imgY,imgW,imgH;
    private UrmButton menu,play;


    public GameOverOverlay(Playing playing){
        this.playing=playing;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int playX= (int) (487*Game.SCALE);
        int menuX= (int) (305*Game.SCALE);
        int y= (int) (170*Game.SCALE);

        play=new UrmButton(playX,y,URM_SIZE,URM_SIZE,0);
        menu=new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
    }

    private void createImg() {
        img= LoadSave.getSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW= (int) (img.getWidth()* Game.SCALE*(1/2.0f));
        imgH= (int) (img.getHeight()* Game.SCALE*(1/2.0f));
        imgX=Game.GAME_WIDTH/2-imgW/2;
        imgY=150;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);

        g.drawImage(img,imgX,imgY,imgH,imgW,null);

        menu.draw(g);
        play.draw(g);
    }

    public void update(){
        menu.update();
        play.update();
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state=Gamestate.MENU;
        }
    }

    private boolean isIn(UrmButton b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }

    public void mouseMove(MouseEvent e){
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu,e))
            menu.setMouseOver(true);
        else if(isIn(play,e))
            play.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e)){
            if(menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state=Gamestate.MENU;
            }
        }
        else if(isIn(play,e)){
            if(play.isMousePressed()) {
                playing.resetAll();

            }

        }
        menu.resetBooleans();
        play.resetBooleans();
    }

    public void mousePressed(MouseEvent e){

        if(isIn(menu,e))
            menu.setMousePressed(true);
        else if(isIn(play,e))
            play.setMousePressed(true);
    }
}
