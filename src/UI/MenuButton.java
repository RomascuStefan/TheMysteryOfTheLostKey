package UI;

import GAMESTATES.Gamestate;
import UTILS.LoadSave;
import static UTILS.Constants.UI.Buttons.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int xPos,yPos,rowIndex,index;
    private int xOffsetCentre=BTN_WIDTH/2;

    private  Gamestate state;
    private BufferedImage[] img;
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int row, Gamestate state){
        this.xPos=xPos;
        this.yPos=yPos;
        this.rowIndex=row;
        this.state=state;

        loadImg();
        initBounds();

    }

    private void initBounds() {
        bounds=new Rectangle(xPos-xOffsetCentre,yPos,BTN_WIDTH,BTN_HEIGHT);
    }

    private void loadImg() {
        img=new BufferedImage[3];
        BufferedImage aux= LoadSave.getSpriteAtlas(LoadSave.BUTTON_ATLAS);
        for(int i=0;i<img.length;i++){
            img[i]=aux.getSubimage(i*BTN_WIDTH_DEFAULT,rowIndex*BTN_HEIGHT_DEFAULT,BTN_WIDTH_DEFAULT,BTN_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g){
        g.drawImage(img[index],xPos-xOffsetCentre,yPos,BTN_WIDTH,BTN_HEIGHT,null);
    }

    public void update(){
        index=0;
        if(mouseOver)
            index=1;
        if(mousePressed)
            index=2;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGamestate(){
        Gamestate.state=state;
    }

    public void resetBooleans(){
        mouseOver=false;
        mousePressed=false;
    }

    public Rectangle getBounds(){
        return bounds;
    }

}
