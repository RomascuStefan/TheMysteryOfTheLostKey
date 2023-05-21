package INPUTS;

import GAMESTATES.Gamestate;
import GAMESTATES.Leaderboard;
import MAIN.GamePanel;

import java.awt.event.*;

public class MouseInputs implements MouseListener, MouseMotionListener, MouseWheelListener {
    private GamePanel gamepanel;
    public MouseInputs(GamePanel gamepanel){
        this.gamepanel=gamepanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state){
            case PLAYING:
                gamepanel.getGame().getPlaying().mouseClicked(e);
                break;
            case LEADERBOARD:
                gamepanel.getGame().getLeaderboard().mouseClicked(e);
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamepanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().mousePressed(e);
                break;
            case LEADERBOARD:
                gamepanel.getGame().getLeaderboard().mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamepanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().mouseReleased(e);
                break;
            case LEADERBOARD:
                gamepanel.getGame().getLeaderboard().mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {


    }
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamepanel.getGame().getMenu().mouseMove(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().mouseMove(e);
                break;
            case LEADERBOARD:
                gamepanel.getGame().getLeaderboard().mouseMove(e);
                break;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    //tip sageti
    }
}
