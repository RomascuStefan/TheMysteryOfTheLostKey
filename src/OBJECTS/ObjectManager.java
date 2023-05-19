package OBJECTS;

import GAMESTATES.Playing;
import LEVELS.Level;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import static UTILS.Constants.ObjectConstants.*;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[] potionImg,chestImg;
    private BufferedImage keyImg;
    private ArrayList<Potion> potions;
    private ArrayList<Chest> chests;
    private ArrayList<Key> keys;


    public ObjectManager(Playing playing){
        this.playing=playing;
        loadImgs();
    }

    public void checkObjectTouch(Rectangle2D.Float hitbox){
        for(Potion p:potions)
            if(p.isActive())
                if(hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffect(p);
                    return;
                }

        for(Key k:keys)
            if(k.isActive())
                if(hitbox.intersects(k.getHitbox())) {
                    k.setActive(false);
                    k.collect();
                    return;
                }
    }
    public void applyEffect(Potion p){
        if(p.getObjType()==BIG_POTION)
            playing.getHero().changeHealth(BIG_POTION_VALUE);
        else if(p.getObjType()==SMALL_POTION)
            playing.getHero().changeHealth(SMALL_POTION_VALUE);
    }
    public void checkChestOpen(Rectangle2D.Float hitbox){
        for(Chest c:chests)
            if(c.isActive())
                if(hitbox.intersects(c.getHitbox()))
                {
                    int type=0;
                    if(c.getObjType()==LEGENDAR_CHEST)
                        type=1;
                    c.setActive(false);
                    potions.add(new Potion((int) c.getHitbox().x, (int) (c.getHitbox().y-150),type));
                    return;
                }
    }

    public void loadObjects(Level newLevel) {
        potions=newLevel.getPotions();
        chests=newLevel.getChests();
        keys=newLevel.getKey();
    }

    private void loadImgs() {
        BufferedImage small= LoadSave.getSpriteAtlas(LoadSave.SMALL_HEAL);
        BufferedImage big= LoadSave.getSpriteAtlas(LoadSave.BIG_HEAL);

        potionImg=new BufferedImage[2];
        potionImg[0]=small;
        potionImg[1]=big;


        BufferedImage common=LoadSave.getSpriteAtlas(LoadSave.COMMON_CHEST);
        BufferedImage rare=LoadSave.getSpriteAtlas(LoadSave.RARE_CHEST);
        BufferedImage legend=LoadSave.getSpriteAtlas(LoadSave.LEGENDAR_CHEST);

        chestImg=new BufferedImage[3];
        chestImg[0]=common;
        chestImg[1]=rare;
        chestImg[2]=legend;

        keyImg=LoadSave.getSpriteAtlas(LoadSave.KEY);


    }

    public void update(){
        for(Potion p:potions)
            if(p.isActive())
                p.update();

        for(Chest c: chests)
            if(c.isActive())
                c.update();
    }

    public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
        drawPotions(g,xLvlOffset,yLvlOffset);
        drawChests(g,xLvlOffset,yLvlOffset);
        drawKeys(g,xLvlOffset,yLvlOffset);
    }

    private void drawKeys(Graphics g, int xLvlOffset, int yLvlOffset) {
        for(Key k : keys)
            if(k.isActive())
                g.drawImage(keyImg,(int) (k.getHitbox().x-k.getxDrawOffset()-xLvlOffset), (int) (k.getHitbox().y-k.getyDrawOffset()-yLvlOffset),KEY_WIDTH,KEY_HEIGHT,null);
    }

    private void drawChests(Graphics g, int xLvlOffset, int yLvlOffset) {
        for(Chest c: chests)
            if(c.isActive()) {
                int type=COMMON_CHEST;
                if (c.getObjType() == RARE_CHEST)
                    type=RARE_CHEST;
                if(c.getObjType()==LEGENDAR_CHEST)
                    type=LEGENDAR_CHEST;

                g.drawImage(chestImg[type], (int) (c.getHitbox().x-c.getxDrawOffset()-xLvlOffset), (int) (c.getHitbox().y-c.getyDrawOffset()-yLvlOffset),CHEST_WIDTH,CHEST_HEIGHT,null);

            }
    }

    private void drawPotions(Graphics g, int xLvlOffset, int yLvlOffset) {
        for(Potion p:potions)
            if(p.isActive()){
                int typeP=0;
                if(p.getObjType()==BIG_POTION)
                    typeP=1;

                g.drawImage(potionImg[typeP], (int) (p.getHitbox().x-p.getxDrawOffset()-xLvlOffset), (int) (p.getHitbox().y-p.getyDrawOffset()-yLvlOffset),POTION_WIDTH,POTION_HEIGHT,null);
            }
    }


    public void resetAllObjects() {
        for(Potion p:potions)
            p.reset();

        for(Chest c: chests)
            c.reset();

        for(Key k:keys)
            k.reset();
    }
}
