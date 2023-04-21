package MAIN;

import INPUTS.KeyboardInputs;
import INPUTS.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static MAIN.Game.GAME_HEIGHT;
import static MAIN.Game.GAME_WIDTH;


public class GamePanel extends JPanel {
    private MouseInputs mouseinputs;
    private KeyboardInputs keyboardinputs;
    private Game game;

    public GamePanel(Game game) {

        mouseinputs=new MouseInputs(this);
        keyboardinputs=new KeyboardInputs(this);
        this.game=game;

        setPanelSize();

        addKeyListener(keyboardinputs);
        addMouseListener(mouseinputs);
        addMouseMotionListener(mouseinputs);
        addMouseWheelListener(mouseinputs);

    }

    private void setPanelSize() {

        setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
        System.out.println("game: "+GAME_WIDTH+"|"+GAME_HEIGHT);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        game.render(g);
    }

    public void updateGame() {

    }

    public Game getGame(){
        return game;
    }
}

