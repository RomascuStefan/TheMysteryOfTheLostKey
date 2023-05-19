package ENTITY;

import GAMESTATES.Playing;
import LEVELS.Level;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static UTILS.Constants.enemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] GoblinMatrix;
    private ArrayList<Goblin> goblinArmy=new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImg();

    }

    public   void loadEnemies(Level level) {
        goblinArmy=level.getGoblins();
    }

    public void update(int[][]lvlData,Hero hero){
        boolean isAnyActive=false;
        for(Goblin gbl : goblinArmy)
            if(gbl.isActive()) {
                gbl.update(lvlData, hero);
                isAnyActive=true;
            }
        if(isAnyActive==false)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g,int xLvlOffset,int yLvlOffset){
        drawGoblinArmy(g,xLvlOffset,yLvlOffset);

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

    }

    public void resetAllEnemies(){
        for(Goblin gbl: goblinArmy)
            gbl.resetEnemy();
    }


}
