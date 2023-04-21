package UI;

import GAMESTATES.Gamestate;
import GAMESTATES.Playing;
import MAIN.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {
    private Playing playing;
    public GameOverOverlay(Playing playing){
        this.playing=playing;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
        g.setColor(Color.white);
        g.drawString("GAME OVER",Game.GAME_WIDTH/2,Game.GAME_HEIGHT/2-100);
        g.drawString("PRESS ESC TO ENTER MAIN MENU",Game.GAME_WIDTH/2-68,Game.GAME_HEIGHT/2+200);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state=Gamestate.MENU;
        }
    }
}
