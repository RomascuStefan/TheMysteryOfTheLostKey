package UTILS;

import MAIN.Game;

public class Constants {

    public static class enemyConstants{
        public static final int GOBLIN=1;//pentru green code de la map data
        public static final int GHOST=2;
        public static final int HOUND=3;
        public static final int IDLE=0;
        public static final int ATTACK=1;
        public static final int TAKE_DAMAGE=2;
        public static final int MOVE=3;
        public static final int DIE=4;

        public static final int GOBLIN_WIDTH_DEFAULT=1248;
        public static final int GOBLIN_HEIGHT_DEFAULT=848;

        public static final int GOBLIN_WIDTH=(int)((1/12.0f)*GOBLIN_WIDTH_DEFAULT* Game.SCALE);
        public static final int GOBLIN_HEIGHT=(int)((1/10.0f)*GOBLIN_HEIGHT_DEFAULT* Game.SCALE);

        public static final int GOBLIN_DRAW_OFFSET_X=(int)((1/12.0f)*406*Game.SCALE);
        public static final int GOBLIN_DRAW_OFFSET_Y=(int)((1/7.35f)*381*Game.SCALE);

        public static int getSpriteAmount(int enemyType,int enemyState){
            switch (enemyType){
                case GOBLIN:
                    switch (enemyState){
                        case IDLE:
                            return 8;
                        case ATTACK:
                            return 12;
                        case TAKE_DAMAGE:
                            return 4;
                        case MOVE:
                            return 6;
                        case DIE:
                            return 9;
                    }
            }
            return 0;
        }

        public static int getMaxHealth(int enemyType){
            switch (enemyType){
                case GOBLIN:
                    return 150;
                case GHOST:
                    return 50;
                case HOUND:
                    return 100;
                default:
                    return 0;
            }
        }

        public static int getEnemyDamage(int enemyType){
            switch (enemyType){
                case GOBLIN:
                    return 65;
                case GHOST:
                    return 30;
                case HOUND:
                    return 50;
                default:
                    return 0;
            }
        }

    }
    public static class UI{
        public static class Buttons{
            public static final int BTN_WIDTH_DEFAULT=140;
            public static final int BTN_HEIGHT_DEFAULT=56;
            public static final int BTN_WIDTH=(int)(BTN_WIDTH_DEFAULT* Game.SCALE);
            public static final int BTN_HEIGHT=(int)(BTN_HEIGHT_DEFAULT* Game.SCALE);
        }
    }

    public static class playerConstants{
        public static final int die=0;
        public static final int jump=1;
        public static final int idle=2;
        public static final int run=3;
        public static final int takeDamage=4;


        public static int getSpriteSize(int player_action){
            switch (player_action){
                case die: return 6;
                case jump, takeDamage: return 4;
                case idle, run: return 8;
                default: return 1;
            }
        }
    }

    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;

    }


}
