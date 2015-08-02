package game.logic;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 02.05.12
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public final class Constants {
    public static final int tankSize = 40;
    public static final int shellSize = 8;
    public static final int gameFieldWidth = 800;
    public static final int gameFieldHeight = 600;
    public static final int stepValue = 8;
    public static final int horizontalCellsCount = 99;
    public static final int verticalCellsCount = 74;

    // размеры объектов на карте
    public static final int brickSize = 8;
    public static final int concreteSize = 16;
    public static final int waterSize = 16;
    public static final int hqSize = 40;
    public static final int explosionSize = 16;
    public static final int bonusSize = 40;

    // картинки
    public static final String G2_SHELL_UP = "/images/G2ShellT.gif";
    public static final String G2_SHELL_DOWN = "/images/G2ShellB.gif";
    public static final String G2_SHELL_LEFT = "/images/G2ShellL.gif";
    public static final String G2_SHELL_RIGHT = "/images/G2ShellR.gif";

    public static final String shellUp = "/images/ShellT.png";
    public static final String shellDown = "/images/ShellB.png";
    public static final String shellLeft = "/images/ShellL.png";
    public static final String shellRight = "/images/ShellR.png";

    public static final String playerTwoTankUp = "/images/Y2ShellT.gif";
    public static final String playerTwoTankDown = "/images/Y2ShellB.gif";
    public static final String playerTwoTankLeft = "/images/Y2ShellL.gif";
    public static final String playerTwoTankRight = "/images/Y2ShellR.gif";

    public static final String configFile = "./settings.properties";
    public static final String defaultConfig = "/config.properties";

    public static final String pauseImage ="/images/Pause.png";
    public static final String gameOverImage = "/images/GameOver.png";
    public static final String youWinImage = "/images/You Win.png";

    public static final String starBonusImage = "/images/STAR.png";
    public static final String lifeBonusImage = "/images/LIFE.png";
    public static final String spadeBonusImage = "/images/SPADE.png";

    // цифровые коды взрывов
    public final static int SMALL_EXPLOSION_CODE = 5;
    public final static int BIG_EXPLOSION_CODE = 6;

    // цифровые коды нажатых клавиш
    public final static int pauseKeyCode = 1;

    // цифровые коды игровых сообщений
    public final static int pauseMessageCode = 1;
    public final static int gameOverMessageCode = 2;
    public final static int youWinMessageCode = 3;

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
