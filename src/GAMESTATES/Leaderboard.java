package GAMESTATES;

import MAIN.Game;
import UI.MenuButton;
import UI.UrmButton;
import UTILS.Database;
import UTILS.LoadSave;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static UTILS.Constants.UI.urmButtons.URM_SIZE;

public class Leaderboard extends State implements StateMethods{

    private Database db;
    private UrmButton home;
    private BufferedImage background;

    public Leaderboard(Game game) {
        super(game);
        db=game.getDB();
        home=new UrmButton(Game.GAME_WIDTH/2-URM_SIZE/2,830,URM_SIZE,URM_SIZE,2);
        background= LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    @Override
    public void update() {
        home.update();
    }


    private boolean isIn(UrmButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(background,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        home.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,50));
        g.drawString("LEADERBOARD",Game.GAME_WIDTH/2 -200,200);
        String champ=db.bestScore();
        g.setColor(Color.pink);
        g.drawString(champ,Game.GAME_WIDTH/2-150,Game.GAME_HEIGHT/2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e){
        if(isIn(home,e))
            home.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(home,e)){
            if(home.isMousePressed()) {
                Gamestate.state=Gamestate.MENU;
            }
        }
        home.resetBooleans();
    }

    @Override
    public void mouseMove(MouseEvent e) {
        home.setMouseOver(false);
        if(isIn(home,e))
            home.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            Gamestate.state=Gamestate.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
