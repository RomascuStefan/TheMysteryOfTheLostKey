package OBJECTS;

import MAIN.Game;

public class Chest extends GameObject{
    public Chest(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(30,30);
        xDrawOffset=(int)(2* Game.SCALE);
        yDrawOffset=(int)(-6*Game.SCALE);
    }

    public void update(){

    }


}
