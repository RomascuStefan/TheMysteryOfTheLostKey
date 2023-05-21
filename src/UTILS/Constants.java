package UTILS;

import MAIN.Game;

public class Constants {

    public static final float GRAVITY=0.04f* Game.SCALE;

    public static class ObjectConstants{
        public static final int ARROW=0;
        public static final int SMALL_POTION=0;
        public static final int BIG_POTION=1;
        public static final int COMMON_CHEST=0;
        public static final int RARE_CHEST=1;
        public static final int LEGENDAR_CHEST=2;
        public static final int KEY=0;


        public static final int SMALL_POTION_VALUE=45;
        public static final int BIG_POTION_VALUE=75;
        public static final int POTION_WIDTH_DEFAULT=512;
        public static final int POTION_HEIGHT_DEFAULT=512;
        public static final int  POTION_WIDTH= (int) (POTION_WIDTH_DEFAULT*Game.SCALE*(1/25.0f));
        public static final int POTION_HEIGHT=(int) (POTION_HEIGHT_DEFAULT*Game.SCALE*(1/25.0f));


        public static final int CHEST_WIDTH_DEFAULT=1604;
        public static final int CHEST_HEIGHT_DEFAULT=1349;
        public static final int CHEST_WIDTH= (int) (CHEST_WIDTH_DEFAULT*Game.SCALE*(1/45.0f));
        public static final int CHEST_HEIGHT=(int) (CHEST_HEIGHT_DEFAULT*Game.SCALE*(1/45.0f));




        public static final int KEY_WIDTH_DEFAULT=64;
        public static final int KEY_HEIGHT_DEFAULT=36;
        public static final int KEY_WIDTH=(int)(KEY_WIDTH_DEFAULT*Game.SCALE*1/2.0f);
        public static final int KEY_HEIGHT=(int)(KEY_HEIGHT_DEFAULT*Game.SCALE*1/2.0f);


        public static final int ARROW_SPEED=5;
        public static final int DOWN_ARROW=0;
        public static final int DOWN_RIGHT_ARROW=1;
        public static final int RIGHT_ARROW=2;
        public static final int UP_RIGHT_ARROW=3;
        public static final int UP_ARROW=4;
        public static final int UP_LEFT_ARROW=5;
        public static final int LEFT_ARROW=6;
        public static final int DOWN_LEFT_ARROW=7;
        public static final int ARROW_WIDTH_DEFAULT=830;
        public static final int ARROW_HEIGHT_DEFAULT=830;
        public static final int ARROW_WIDTH= (int) (ARROW_WIDTH_DEFAULT*Game.SCALE*(1/20.0f));
        public static final int ARROW_HEIGHT= (int) (ARROW_HEIGHT_DEFAULT*Game.SCALE*(1/20.0f));


    }

    public static class enemyConstants{
        public static final int GOBLIN=1;//pentru green code de la map data
        public static final int HOUND=2;
        public static final int GHOST=3;


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


        public static final int HOUND_WIDTH_DEFAULT=256;
        public static final int HOUND_HEIGHT_DEFAULT=256;
        public static final int HOUND_WIDTH= (int) (HOUND_WIDTH_DEFAULT*Game.SCALE*(1/3.0f));
        public static final int HOUND_HEIGHT=(int) (HOUND_HEIGHT_DEFAULT*Game.SCALE*(1/3.0f));
        public static final int HOUND_DRAW_OFFSET_X=(int)(30*Game.SCALE);
        public static final int HOUND_DRAW_OFFSET_Y=(int)(62.5f*Game.SCALE);


        public static final int GHOST_WIDTH_DEFAULT=45;
        public static final int GHOST_HEIGHT_DEFAULT=45;
        public static final int GHOST_WIDTH= (int) (GHOST_WIDTH_DEFAULT*Game.SCALE*(4/5.0f));
        public static final int GHOST_HEIGHT= (int) (GHOST_HEIGHT_DEFAULT*Game.SCALE*(4/5.0f));
        public static final int GHOST_DRAW_OFFSET_X=(int)(10*Game.SCALE);
        public static final int GHOST_DRAW_OFFSET_Y=(int)(20f*Game.SCALE);



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
                case HOUND:
                    switch (enemyState){
                        case IDLE, DIE, TAKE_DAMAGE:
                            return 3;
                        case ATTACK, MOVE:
                            return 5;
                    }
                case GHOST:
                    switch (enemyState){
                        case IDLE, MOVE:
                            return 6;
                        case ATTACK:
                            return 8;
                        case TAKE_DAMAGE, DIE:
                            return 4;
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
                    return 25;
                case HOUND:
                    return 30;
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
        public static class pausedButtons{
            public static final int SOUND_SIZE_DEFAULT=42;
            public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT* Game.SCALE);
        }
        public static class urmButtons{
            public static final int URM_DEFAULT_SIZE=56;
            public static final int URM_SIZE= (int) (URM_DEFAULT_SIZE* Game.SCALE*3/4.0f);
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
