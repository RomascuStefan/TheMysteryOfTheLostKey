package INPUTS;

import GAMESTATES.Gamestate;
import MAIN.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamepanel;
    public KeyboardInputs(GamePanel gamepanel) {
            this.gamepanel=gamepanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamepanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().keyPressed(e);
                break;
            case LEADERBOARD:
                gamepanel.getGame().getLeaderboard().keyPressed(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamepanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().keyReleased(e);
                break;
        }


    }
}
