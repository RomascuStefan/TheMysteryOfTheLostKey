package ENTITY;

import GAMESTATES.Playing;
import LEVELS.Level;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import static UTILS.Constants.enemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] GoblinMatrix;
    private BufferedImage[][] HoundMatrix;
    private BufferedImage[][]GhostMatrix;
    private ArrayList<Goblin> goblinArmy=new ArrayList<>();
    private ArrayList<Hound> houndArmy=new ArrayList<>();
    private ArrayList<Ghost> ghostArmy=new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImg();

    }

    public   void loadEnemies(Level level) {
        goblinArmy=level.getGoblins();
        houndArmy= level.getHounds();
        ghostArmy=level.getGhosts();
    }

    public void update(int[][]lvlData,Hero hero){
        boolean isAnyActive=false;
        for(Goblin gbl : goblinArmy)
            if(gbl.isActive()) {
                gbl.update(lvlData, hero);
                isAnyActive=true;
            }
        for(Hound h :houndArmy)
            if(h.isActive()){
                h.update(lvlData,hero);
                isAnyActive=true;
            }

        for(Ghost gh :ghostArmy)
            if(gh.isActive()) {
                gh.update(lvlData, hero);
                isAnyActive=true;
            }

        if(isAnyActive==false)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
        drawGoblinArmy(g,xLvlOffset,yLvlOffset);
        drawHoundArmy(g,xLvlOffset,yLvlOffset);
        drawGhostArmy(g,xLvlOffset,yLvlOffset);

    }

    private void drawGhostArmy(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Ghost gh : ghostArmy)
            if (gh.isActive()) {
                g.drawImage(GhostMatrix[gh.getState()][gh.getAniIndex()],
                        (int) (gh.getHitBox().x) - xLvlOffset - GHOST_DRAW_OFFSET_X + gh.flipX(),
                        (int) (gh.getHitBox().y) - yLvlOffset - GHOST_DRAW_OFFSET_Y,
                        GHOST_WIDTH * gh.flipW(),
                        GHOST_HEIGHT,
                        null);
                gh.drawHitBox(g,xLvlOffset,yLvlOffset);
                gh.drawAttackBox(g,xLvlOffset,yLvlOffset);
            }
    }


    private void drawGoblinArmy(Graphics g,int xLvlOffset,int yLvlOffset) {
        for (Goblin gbl : goblinArmy)
            if (gbl.isActive()) {
                g.drawImage(GoblinMatrix[gbl.getState()][gbl.getAniIndex()],
                        (int) (gbl.getHitBox().x) - xLvlOffset - GOBLIN_DRAW_OFFSET_X + gbl.flipX(),
                        (int) (gbl.getHitBox().y) - yLvlOffset - GOBLIN_DRAW_OFFSET_Y,
                        GOBLIN_WIDTH * gbl.flipW(),
                        GOBLIN_HEIGHT,
                        null);
//                gbl.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
    }
    private void drawHoundArmy(Graphics g,int xLvlOffset,int yLvlOffset) {
        for (Hound h : houndArmy)
            if (h.isActive()) {
                g.drawImage(HoundMatrix[h.getState()][h.getAniIndex()],
                        (int) (h.getHitBox().x) - xLvlOffset - HOUND_DRAW_OFFSET_X + h.flipX(),
                        (int) (h.getHitBox().y) - yLvlOffset - HOUND_DRAW_OFFSET_Y,
                        HOUND_WIDTH * h.flipW(),
                        HOUND_HEIGHT,
                        null);
//                h.drawHitBox(g,xLvlOffset,yLvlOffset);
//                h.drawAttackBox(g,xLvlOffset,yLvlOffset);
            }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for (Goblin gbl : goblinArmy)
            if (gbl.isActive())
                if (attackBox.intersects(gbl.getHitBox())) {
                    //attack
                    return;
                }
    }

    private void loadEnemyImg() {
        GoblinMatrix=new BufferedImage[5][12];
        BufferedImage aux= LoadSave.getSpriteAtlas(LoadSave.GOBLIN_SPRITE);
        for(int j=0;j<GoblinMatrix.length;j++)
            for(int i=0;i<GoblinMatrix[j].length;i++)
                GoblinMatrix[j][i]=aux.getSubimage(i*GOBLIN_WIDTH_DEFAULT,j*GOBLIN_HEIGHT_DEFAULT,GOBLIN_WIDTH_DEFAULT,GOBLIN_HEIGHT_DEFAULT);


        HoundMatrix=new BufferedImage[5][5];
        BufferedImage aux2=LoadSave.getSpriteAtlas(LoadSave.HOUND_SPRITE);
        for(int j=0;j< HoundMatrix.length;j++)
            for(int i=0;i<HoundMatrix[j].length;i++)
                HoundMatrix[j][i]=aux2.getSubimage(i*HOUND_WIDTH_DEFAULT,j*HOUND_HEIGHT_DEFAULT,HOUND_WIDTH_DEFAULT,HOUND_HEIGHT_DEFAULT);


        GhostMatrix=new BufferedImage[5][8];
        BufferedImage aux3=LoadSave.getSpriteAtlas(LoadSave.GHOST_SPRITE);
        for(int j=0;j< GhostMatrix.length;j++)
            for(int i=0;i<GhostMatrix[j].length;i++)
                GhostMatrix[j][i]=aux3.getSubimage(i*GHOST_WIDTH_DEFAULT,j*GHOST_HEIGHT_DEFAULT,GHOST_WIDTH_DEFAULT,GHOST_HEIGHT_DEFAULT);


    }

    public void resetAllEnemies(){
        for(Goblin gbl: goblinArmy)
            gbl.resetEnemy();

        for(Hound h: houndArmy)
            h.resetEnemy();

        for (Ghost gh : ghostArmy)
            gh.resetEnemy();

    }


}
