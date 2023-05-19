package OBJECTS;

import MAIN.Game;

import static UTILS.Constants.ObjectConstants.KEY;

public class Key extends GameObject{
    private static int numberColected=0;
    private static final int maxNumberColected=3;
    public Key(int x, int y) {
        super(x, y, KEY);
        initHitbox(7,14);
        xDrawOffset=(int)(3* Game.SCALE);
        yDrawOffset=(int)(-15* Game.SCALE);
    }

    public void reset(){
        active=true;
        numberColected=0;
    }

    public void collect() {
        numberColected++;
    }
}
