package ENTITY;

import GAMESTATES.Playing;
import MAIN.Game;
import UTILS.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static UTILS.Constants.GRAVITY;
import static UTILS.Constants.enemyConstants.IDLE;
import static UTILS.Constants.playerConstants.*;
import static UTILS.HelpMethods.*;

public class Hero extends Entity{

    private BufferedImage[][] heroAnimation;
    private  int[][] lvlData;
    private float xDrawOffset=70* Game.SCALE;
    private float yDrawOffset=43* Game.SCALE;



    private int  animationSpeed=12;

    private boolean left,up,right;
    private boolean moving=false,aim=false,shoot=false;


//jumping

    private float jumpSpeed=-2.25f* Game.SCALE;
    private float fallSpeedAfterCollision=0.5f* Game.SCALE;


    //HP + STATUS
    private BufferedImage statusBarImg;

    private int statusBarWIdth= (int) (137*Game.SCALE);
    private int statusbarHeight= (int) (19*Game.SCALE);
    private int statusBarX=(int)(10*Game.SCALE);
    private int statusBarY=(int)(10*Game.SCALE);

    private int healthBarWidth=(int)(127*Game.SCALE);
    private int healthBarHeight=(int)(12*Game.SCALE);
    private int startHealthBarX=(int)(5.5f*Game.SCALE);
    private int startHealthBarY=(int)(3.80f*Game.SCALE);


    private int healthWidth=healthBarWidth;

    private int flipX=0;
    private int flipW=1;

    private Playing playing;

    public Hero(float x, float y, int width, int height,Playing playing) {
        super(x, y,width,height);
        this.playing=playing;
        this.state=idle;
        this.maxHealth=100;
        this.currentHealth=maxHealth;
        this.walkSpeed=1.25f * Game.SCALE;

        loadAnimation();
        initHitBox(17,30);
    }

    public void setSpawn(Point spawn){
        this.x= spawn.x;
        this.y=spawn.y;

        hitBox.x=x;
        hitBox.y=y;
    }

    public void update(){
        checkOutOfMap();
        updateHealthBar();
        if(currentHealth<=0) {
            if(state!=die) {
                state = die;
                aniTick=0;
                aniIndex=0;
                playing.setHeroDying(true);
            } else if(aniIndex>=getSpriteSize(die)-1 && aniTick>=animationSpeed-1){
                playing.setGameOver(true);
            }else{
                updateAnimation();
            }
            return;
        }

        updatePos();

        checkInteraction();
        checkChestTouched();

        updateAnimation();
        setAnimation();
    }

    private void checkChestTouched() {
        playing.checkChestTouched(hitBox);
    }

    private void checkInteraction() {
        playing.checkPotionTouched(hitBox);
    }

    private void checkOutOfMap() {
        if(hitBox.y>=940)
            changeHealth(-maxHealth);
    }

    private void updateHealthBar() {
        healthWidth=(int)( (currentHealth/(float)maxHealth) * healthBarWidth);
    }

    public void render(Graphics g,int xLvlOffset,int yLvlOffset){
        g.drawImage(heroAnimation[state][aniIndex],
                (int) (hitBox.x - xDrawOffset) - xLvlOffset+flipX,
                (int) (hitBox.y - yDrawOffset - yLvlOffset),
                width*flipW,
                height,
                null);

        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg,statusBarX,statusBarY,statusBarWIdth,statusbarHeight,null);
        g.setColor(Color.RED);
        g.fillRect(startHealthBarX+statusBarX,startHealthBarY+statusBarY,healthWidth,healthBarHeight);
    }


    private void updateAnimation() {
        aniTick++;
        if (aniTick >= animationSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteSize(state))
                aniIndex = 0;
        }
    }


    private void setAnimation() {
        int startAni=state;

        if (moving)
            state=run;
        else
            state=idle;
        if(inAir)
            if(airSpeed<0)
                state=jump;

        if(startAni !=state)
            resetAniTick();

    }

    private void resetAniTick() {
        aniIndex=0;
        aniTick=0;
    }

    private void updatePos() {
        moving=false;

        if(up)
            jump();

        if(!inAir)
            if(!left && !right || left && right)
                return;

        float xSpeed=0;

        if(left) {
            xSpeed -= walkSpeed;
            flipX=width;
            flipW=-1;
        }

        if(right) {
            xSpeed += walkSpeed;
            flipX=0;
            flipW=1;
        }

        if(!inAir){
            if(!isEntityOnFloor(hitBox,lvlData)){
                inAir=true;
            }
        }


        if(inAir){
            if(canMoveHere(hitBox.x, hitBox.y+airSpeed,hitBox.width,hitBox.height,lvlData )){
                hitBox.y+=airSpeed;
                airSpeed+=GRAVITY;
                updateXpos(xSpeed);
            }
            else {
                hitBox.y=getEntityYPosUnderAbove(hitBox,airSpeed);
                if(airSpeed>0)
                    resetInAir();
                else
                    airSpeed=fallSpeedAfterCollision;
                updateXpos(xSpeed);
            }
        }
        else{
            updateXpos(xSpeed);
        }
        moving=true;

    }

    private void jump() {
        if(inAir)
            return;

        inAir=true;
        airSpeed=jumpSpeed;
    }

    private void resetInAir() {
        inAir=false;
        airSpeed=0;
    }

    private void updateXpos(float xSpeed) {
        if(canMoveHere(hitBox.x+xSpeed, hitBox.y  ,hitBox.width,hitBox.height,lvlData)) {
            hitBox.x += xSpeed;
        }
        else {
            hitBox.x=getEntityXposNextToWall(hitBox,xSpeed);
        }
    }

    public void changeHealth(int damage){
            currentHealth += damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                //gameOver();
            } else if (currentHealth >= maxHealth)
                currentHealth = maxHealth;

    }

    private void loadAnimation() {
        BufferedImage hero = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        heroAnimation = new BufferedImage[5][8];
        for (int i = 0; i < heroAnimation.length; i++)
            for (int j = 0; j < heroAnimation[i].length; j++)
                heroAnimation[i][j] = hero.getSubimage(j * 150, i * 150, 150, 150);


        statusBarImg=LoadSave.getSpriteAtlas(LoadSave.HEALTH_BAR);
    }

    public void loadLvlData(int[][]lvlData){
        this.lvlData=lvlData;
        if(!isEntityOnFloor(hitBox,lvlData))
            inAir=true;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isAim(){
        return aim;
    }
    public  void setAim(boolean aim){
        this.aim=aim;
    }

    public void resetBoolean() {
        left=right=up=false;
    }

    public void setShoot(boolean shoot) {
        if(aim)
            shoot=true;
    }

    public boolean isShoot(){
        return shoot;
    }

    public void resetAll() {
        resetBoolean();
        inAir=false;
        moving=false;
        state=IDLE;
        currentHealth=maxHealth;

        hitBox.x=x;
        hitBox.y=y;

        if(!isEntityOnFloor(hitBox,lvlData))
            inAir=true;
    }
}
