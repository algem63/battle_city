package game.logic;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 02.05.12
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public final class Constants {
    public static final int TANK_SIZE = 40;
    public static final int SHELL_SIZE = 8;
    public static final int GAME_FIELD_WIDTH = 800;
    public static final int GAME_FIELD_HEIGHT = 600;
    public static final int STEP_VALUE = 8;
    public static final int HORIZONTAL_CELLS_COUNT = 99;
    public static final int VERTICAL_CELLS_COUNT = 74;

    // размеры объектов на карте
    public static final int BRICK_SIZE = 8;
    public static final int CONCRETE_SIZE = 16;
    public static final int WATER_SIZE = 16;
    public static final int HQ_SIZE = 40;
    public static final int EXPLOSION_SIZE = 16;
    public static final int BONUS_SIZE = 40;

    // картинки
    public static final String G2_SHELL_UP = "/images/G2ShellT.gif";
    public static final String G2_SHELL_DOWN = "/images/G2ShellB.gif";
    public static final String G2_SHELL_LEFT = "/images/G2ShellL.gif";
    public static final String G2_SHELL_RIGHT = "/images/G2ShellR.gif";

    public static final String SHELL_UP = "/images/ShellT.png";
    public static final String SHELL_DOWN = "/images/ShellB.png";
    public static final String SHELL_LEFT = "/images/ShellL.png";
    public static final String SHELL_RIGHT = "/images/ShellR.png";

    public static final String PLAYER_TWO_TANK_UP = "/images/Y2ShellT.gif";
    public static final String PLAYER_TWO_TANK_DOWN = "/images/Y2ShellB.gif";
    public static final String PLAYER_TWO_TANK_LEFT = "/images/Y2ShellL.gif";
    public static final String PLAYER_TWO_TANK_RIGHT = "/images/Y2ShellR.gif";

    public static final String CONFIG_FILE = "./settings.properties";
    public static final String DEFAULT_CONFIG = "/config.properties";

    public static final String PAUSE_IMAGE ="/images/Pause.png";
    public static final String GAME_OVER_IMAGE = "/images/GameOver.png";
    public static final String YOU_WIN_IMAGE = "/images/You Win.png";

    public static final String STAR_BONUS_IMAGE = "/images/STAR.png";
    public static final String LIFE_BONUS_IMAGE = "/images/LIFE.png";
    public static final String SPADE_BONUS_IMAGE = "/images/SPADE.png";

    // цифровые коды взрывов
    public final static int SMALL_EXPLOSION_CODE = 5;
    public final static int BIG_EXPLOSION_CODE = 6;

    // цифровые коды нажатых клавиш
    public final static int PAUSE_KEY_CODE = 1;

    // цифровые коды игровых сообщений
    public final static int PAUSE_MESSAGE_CODE = 1;
    public final static int GAME_OVER_MESSAGE_CODE = 2;
    public final static int YOU_WIN_MESSAGE_CODE = 3;

    // цифровые коды направлений
    public final static int UP = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;
    public final static int RIGHT = 4;

    // цифровые коды объектов на карте
    public final static int BRICK_CODE = 1;
    public final static int CONCRETE_CODE = 2;
    public final static int WATER_CODE = 3;
    public final static int HQ_CODE = 4;
    public final static int EXPLOSION_CODE = 5;
    public final static int TANK_CODE = 6;

    //16-иричные коды цветов
    public final static int BLACK_BACKGROUND = 0xff000000;
}
