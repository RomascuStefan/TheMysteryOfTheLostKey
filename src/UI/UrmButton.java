package UI;

import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.urmButtons.URM_DEFAULT_SIZE;
import static UTILS.Constants.UI.urmButtons.URM_SIZE;

public class UrmButton extends PausedButton{
    private BufferedImage[] imgs;
    private int rowIndex,index;
    private boolean mouseOver,mousePressed;


    public UrmButton(int x, int y, int width, int height,int rowIndex) {
        super(x, y, width, height);
        this.rowIndex=rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp= LoadSave.getSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs=new BufferedImage[3];
        for(int i=0;i<imgs.length;i++)
            imgs[i]=temp.getSubimage(i*URM_DEFAULT_SIZE,rowIndex*URM_DEFAULT_SIZE,URM_DEFAULT_SIZE,URM_DEFAULT_SIZE);
    }

    public void update(){
        index=0;
        if(mouseOver)
            index=1;
        if(mousePressed)
            index=2;
    }
    public void draw(Graphics g){
        g.drawImage(imgs[index],x,y,URM_SIZE,URM_SIZE,null);
    }

    public void resetBooleans(){
        mouseOver=false;
        mousePressed=false;
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
}
