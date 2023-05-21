package UI;

import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.pausedButtons.SOUND_SIZE_DEFAULT;

public class SoundButton extends PausedButton{
    private BufferedImage[][] soundImg;
    private int rowIndex,colIndex;
    private boolean mouseOver,mousePressed;
    private boolean muted;
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImg();

    }

    private void loadSoundImg() {
        BufferedImage temp= LoadSave.getSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImg=new BufferedImage[2][3];
        for(int j=0;j<soundImg.length;j++)
            for(int i=0;i<soundImg[j].length;i++)
                soundImg[j][i]=temp.getSubimage(i*SOUND_SIZE_DEFAULT,j*SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT);
    }

    public void update(){
        if(muted==true)
            rowIndex=1;
        else
            rowIndex=0;

        colIndex=0;
        if(mouseOver)
            colIndex=1;
        if(mousePressed)
            colIndex=2;
    }

    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
    }

    public void draw(Graphics g){
        g.drawImage(soundImg[rowIndex][colIndex],x,y,width,height,null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
