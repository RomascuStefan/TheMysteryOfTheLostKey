package OBJECTS;

import ENTITY.Hero;
import GAMESTATES.Playing;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static UTILS.Constants.ObjectConstants.*;

public class ArrowManager {
    private Playing playing;
    private ArrayList<Arrow> projectiles=new ArrayList<>();
    private BufferedImage[]proj_img;

    public ArrowManager(Playing playing){
        this.playing=playing;
        importImg();
    }

    private void importImg(){
        BufferedImage atlas= LoadSave.getSpriteAtlas(LoadSave.ARROW_SPRITE);
        proj_img=new BufferedImage[8];
        for(int i=0;i<8;i++)
            proj_img[i]=atlas.getSubimage(i*ARROW_WIDTH_DEFAULT,0,ARROW_WIDTH_DEFAULT,ARROW_HEIGHT_DEFAULT);


    }

    public void newArrow(Hero hero,int x,int y){
        //mouse x,y
        int orientation=getArrowOrientation(hero,x,y);

        int xDist= (int) Math.abs(hero.getHitBox().x - x);
        int yDist= (int) Math.abs(hero.getHitBox().y-y);
        int totalDist= xDist+yDist;

        float xPer=(float) xDist/totalDist;

        float xSpeed=xPer*ARROW_SPEED;
        float ySpeed=ARROW_SPEED-xSpeed;

        if(hero.getHitBox().x>x)
            xSpeed*=-1;
        if(hero.getHitBox().y>y)
            ySpeed*=-1;

        projectiles.add(new Arrow(hero.getHitBox().x,hero.getHitBox().y,xSpeed,ySpeed,orientation));
    }


    public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
        for(Arrow a : projectiles){
            g.drawImage(proj_img[a.getOrientation()], (int) a.getPos().x-xLvlOffset, (int) a.getPos().y-yLvlOffset,ARROW_WIDTH,ARROW_HEIGHT,null);
        }
    }

    public void update(){
        for(Arrow a : projectiles){
            a.move();
        }
    }

    private int getArrowOrientation(Hero hero, int x, int y){
        if (x >hero.getHitBox().x && x<hero.getHitBox().x-hero.getHitBox().width) {
            if (y > hero.getHitBox().y) {
                return UP_ARROW;
            } else if (y < hero.getHitBox().y) {
                return DOWN_ARROW;
            }
        } else if (y> hero.getHitBox().y && y<hero.getHitBox().y+hero.getHitBox().height) {
            if (x > hero.getHitBox().x) {
                return RIGHT_ARROW;
            } else {
                return LEFT_ARROW;
            }
        } else if (x > hero.getHitBox().x && y > hero.getHitBox().y) {
            return UP_RIGHT_ARROW;
        } else if (x < hero.getHitBox().x && y > hero.getHitBox().y) {
            return UP_LEFT_ARROW;
        } else if (x < hero.getHitBox().x && y < hero.getHitBox().y) {
            return DOWN_LEFT_ARROW;
        } else if (x > hero.getHitBox().x && y < hero.getHitBox().y) {
            return DOWN_RIGHT_ARROW;
        }
        return RIGHT_ARROW;
    }

}
