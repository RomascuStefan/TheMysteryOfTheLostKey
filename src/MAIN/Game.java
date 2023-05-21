package MAIN;

import GAMESTATES.Gamestate;
import GAMESTATES.Menu;
import GAMESTATES.Playing;
import UTILS.Database;

import javax.swing.*;
import java.awt.*;

public class Game implements Runnable{
    private final int UPS_SET=200;
    private final int FPS_SET=120;
    private  Thread GameThred;
    private  GameWindow gamewindow;
    private GamePanel gamepanel;


    private Playing playing;
    private Menu menu;


    public  final static int TILE_DEFAULT_SIZE=32;
    public final static float SCALE=2.25f;
    public final static int TILE_IN_WIDTH=24;
    public final static int TILE_IN_HEIGHT=14;
    public final static int TILE_SIZE=(int)(TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH=TILE_SIZE*TILE_IN_WIDTH;
    public final static int GAME_HEIGHT=TILE_SIZE*TILE_IN_HEIGHT;

    private Database db;
    private String name;
    private int score=0;


    public Game() {
        initClass();

        gamepanel=new GamePanel(this);
        gamewindow=new GameWindow(gamepanel);
        gamepanel.setFocusable(true);
        gamepanel.requestFocus();
        StartGameLoop();

    }

    private void getName(){
        name= JOptionPane.showInputDialog("NUMELE JUCATORULUI:");
    }

    private void initClass() {
        menu=new Menu(this);
        playing=new Playing(this);
        db= new Database();
    }
    public void update() {

        switch (Gamestate.state){
            case MENU:
                if(name==null){
                    getName();
                }
                score=0;
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                break;
            case QUIT:
                closeDB();
                System.exit(0);
        }
    }


    private void closeDB() {
        db.close();
    }

    public void render(Graphics g){
        switch (Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;

        }

    }
    @Override
    public void run() {

        double timePF=1_000_000_000.0/FPS_SET;
        double timePU=1_000_000_000.0/UPS_SET;


        long prevTime=System.nanoTime();
        int updates=0;

        int frames=0;
        long lastCheck=System.currentTimeMillis();

        double deltaUpdate=0;
        double deltaFrames=0;

        while(true){
            long curentTime=System.nanoTime();


            deltaUpdate+=(curentTime-prevTime)/timePU;
            deltaFrames+=(curentTime-prevTime)/timePF;
            prevTime=curentTime;
    //in caz in care este lag timpul pierdut va fi recuperat
            if(deltaUpdate>=1){
                update();
                updates++;
                deltaUpdate--;
            }

            if(deltaFrames>=1){
                gamepanel.repaint();
                frames++;
                deltaFrames--;

            }

            if(System.currentTimeMillis()-lastCheck>=1000){
                lastCheck=System.currentTimeMillis();
                System.out.println("FPS: "+frames+"| UPS: "+updates);
                frames=0;
                updates=0;
                updateScore();
            }
        }
    }




    private void StartGameLoop(){
        GameThred=new Thread(this);
        GameThred.start();
    }



    public void windowFocusLost() {
        if(Gamestate.state==Gamestate.PLAYING)
                playing.getHero().resetBoolean();
    }

    public Menu getMenu(){
        return  menu;
    }
    public  Playing getPlaying(){
        return  playing;
    }

    public void save(int score) {
        db.saveScoreToDatabase(name,score);
    }

    public int getScore() {
        return score;
    }
    public void updateScore(){
        score++;
    }
}
