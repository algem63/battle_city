package game.logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Cодержит настройки игры
 */
public class GameConfig {

    /**
     * Единственный инстанс класса
     */
    private static GameConfig instance = null;

    /**
     * Настройки, считываемые из файла
     */
    private Properties gameConfig;

    /**
     * Файл с настройками
     */
    private File configFile;

    /**
     * Используется для чтения настроек из файла
     */
    private InputStream propertyReader;

    /**
     * Используется для записи настроек в файл
     */
    private FileOutputStream configWriter;

    /**
     * Лог игры
     */
    private File logFile;

    /**
     * Используется для записи данных в лог
     */
    private FileWriter logWriter;

    /**
     * Изображения объекта HQ, соответствующие разным уровням его развития
     */
    private BufferedImage headquartersImageL1, headquartersImageL2, headquartersImageL3, headquartersImageL4,
            headquartersImageL5, headquartersImageL6, headquartersImageL7;

    /**
     * Изображение кирпича
     */
    private BufferedImage brickImage;

    /**
     * Изображение бетонного блока
     */
    private BufferedImage concreteImage;

    /**
     * Изображение блока с водой
     */
    private BufferedImage waterImage;

    /**
     * Картинка с танком на стартовом экране игры
     */
    private BufferedImage tankImage;
    //TODO Возможно, графику нужно убрать в отдельный класс
    /**
     * Изображение танка первого игрока
     */
    private BufferedImage playerOneTankImage;

    /**
     * Изображение танка второго игрока
     */
    private BufferedImage playerTwoTankImage;

    /**
     * Надпись PAUSE
     */
    private BufferedImage pauseImage;

    /**
     * Надпись YOU WIN
     */
    private BufferedImage youWinImage;

    /**
     * Надпись GAME OVER
     */
    private BufferedImage gameOverImage;

    /**
     * Изображение бонуса 'Star'
     */
    private BufferedImage starBonusImage;

    /**
     * Изображение бонуса 'Life'
     */
    private BufferedImage lifeBonusImage;

    /**
     * Изображение бонуса 'Spade'
     */
    private BufferedImage spadeBonusImage;

    /**
     * Изображение взрыва снаряда
     */
    private Image explosionImage;

    /**
     * Изображение взрыва танка
     */
    private Image tankExplosionImage;

    /**
     * Текущий уровень игры
     */
    int currentLevel;

    private GameConfig() {
        // загружаем настройки
        configFile = new File(Constants.CONFIG_FILE);
        if (!configFile.exists()) {
            createDefaultConfig();
        } else {
            loadConfig();
        }
        // удаляем файл лога, если он есть
        logFile = new File("error.log");
        if (logFile.exists()) {
            logFile.delete();
        }
        currentLevel = 1;
        loadGraphics();
    }

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    /**
     * Возвращает значение настройки игры
     * @param key ключ, идентифицирующий настройку
     * @return значение настройки
     */
    public String getProperty(final String key) {
        return gameConfig.getProperty(key);
    }

    /**
     * Устанавливает значение настройки игры
     * @param prop  ключ, идентифицирующий настройку
     * @param value значение настройки
     */
    public void setProperty(final String prop, final String value) {
        gameConfig.setProperty(prop, value);
    }

    /**
     * Создает файт с настройками по-умолчанию, если его не существует
     */
    public void createDefaultConfig() {
        try {
            logFile = new File("error.log");
            propertyReader = getClass().getResourceAsStream(Constants.DEFAULT_CONFIG);
            gameConfig = new Properties();
            gameConfig.load(propertyReader);
            configFile.createNewFile();
            configWriter = new FileOutputStream(configFile);
            gameConfig.store(configWriter, null);
        }
        catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } finally {
            try {
                propertyReader.close();
                configWriter.close();
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            }
        }
    }

    /**
     * Загружает настройки игры из файла
     */
    public void loadConfig() {
        try {
            propertyReader = new FileInputStream(Constants.CONFIG_FILE);
            gameConfig = new Properties();
            gameConfig.load(propertyReader);
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } finally {
            try {
                propertyReader.close();
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            }
        }
    }

    /**
     * Сохраняет настройки игры в файл
     */
    public void saveConfig() {
        try {
            if (configWriter == null) {
                configWriter = new FileOutputStream(Constants.CONFIG_FILE);
            }
            gameConfig.store(new FileOutputStream(Constants.CONFIG_FILE), null);
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } finally {
            if (configWriter != null) {
                try {
                    configWriter.close();
                } catch (IOException e) {
                    GameConfig.getInstance().writeErrorMessageToLog(e);
                }
            }
        }
    }

    /**
     * Проверяет, назначена ли заданная клавиша на какую-либо функцию
     * @param key код назначаемой клавиши
     * @return
     */
    public String checkTheFunctionKeyAssignedTo(final int key) {
        Set<String> keys = gameConfig.stringPropertyNames();
        String property = null;
        for(String k: keys) {
            if (gameConfig.getProperty(k).equals(String.valueOf(key))) {
                property = k;
            }
        }
        return property;
    }
    
    public void checkKeyBusy(final int key) {
        Set<String> keys = gameConfig.stringPropertyNames();
        for(String k: keys) {
            if (gameConfig.getProperty(k).equals(String.valueOf(key))) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Key " + KeyEvent.getKeyText(key) + " already assigned for " + k,
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public boolean containsValue(final int value) {
        return gameConfig.containsValue(String.valueOf(value));
    }

    public void loadGraphics() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        try {
            brickImage = ImageIO.read(getClass().getResource("/images/brick.bmp"));
            concreteImage = ImageIO.read(getClass().getResource("/images/concrete.bmp"));
            waterImage = ImageIO.read(getClass().getResource("/images/water.bmp"));
            headquartersImageL1 = ImageIO.read(getClass().getResource("/images/HeadQL1.bmp"));
            headquartersImageL2 = ImageIO.read(getClass().getResource("/images/HeadQL2.bmp"));
            headquartersImageL3 = ImageIO.read(getClass().getResource("/images/HeadQL3.bmp"));
            headquartersImageL4 = ImageIO.read(getClass().getResource("/images/HeadQL4.bmp"));
            headquartersImageL5 = ImageIO.read(getClass().getResource("/images/HeadQL5.bmp"));
            headquartersImageL6 = ImageIO.read(getClass().getResource("/images/HeadQL6.bmp"));
            headquartersImageL7 = ImageIO.read(getClass().getResource("/images/HeadQL7.bmp"));
            tankImage = ImageIO.read(getClass().getResource("/images/TANK.jpg"));
            playerOneTankImage = ImageIO.read(getClass().getResource("/images/G2ShellT1.bmp"));
            playerTwoTankImage = ImageIO.read(getClass().getResource("/images/Y2ShellT1.bmp"));
            explosionImage = kit.getImage(getClass().getResource("/images/Explosion.gif"));
            tankExplosionImage = kit.getImage(getClass().getResource("/images/TankExplosion.gif"));
            pauseImage = ImageIO.read(getClass().getResource(Constants.PAUSE_IMAGE));
            gameOverImage = ImageIO.read(getClass().getResource(Constants.GAME_OVER_IMAGE));
            lifeBonusImage = ImageIO.read(getClass().getResource(Constants.LIFE_BONUS_IMAGE));
            spadeBonusImage = ImageIO.read(getClass().getResource(Constants.SPADE_BONUS_IMAGE));
            starBonusImage = ImageIO.read(getClass().getResource(Constants.STAR_BONUS_IMAGE));
            youWinImage = ImageIO.read(getClass().getResource(Constants.YOU_WIN_IMAGE));
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        }
    }


    public BufferedImage getBrickImage() {
        return brickImage;
    }

    public BufferedImage getConcreteImage() {
        return concreteImage;
    }

    public BufferedImage getHeadquartersImageL1() {
        return headquartersImageL1;
    }

    public BufferedImage getHeadquartersImageL2() {
        return headquartersImageL2;
    }

    public BufferedImage getHeadquartersImageL3() {
        return headquartersImageL3;
    }

    public BufferedImage getHeadquartersImageL4() {
        return headquartersImageL4;
    }

    public BufferedImage getHeadquartersImageL5() {
        return headquartersImageL5;
    }

    public BufferedImage getHeadquartersImageL6() {
        return headquartersImageL6;
    }

    public BufferedImage getHeadquartersImageL7() {
        return headquartersImageL7;
    }

    public BufferedImage getWaterImage() {
        return waterImage;
    }

    public BufferedImage getTankImage() {
        return tankImage;
    }

    public BufferedImage getPlayerOneTankImage() {
        return playerOneTankImage;
    }

    public BufferedImage getPlayerTwoTankImage() {
        return playerTwoTankImage;
    }

    public Image getExplosionImage() {
        return explosionImage;
    }

    public Image getTankExplosionImage() {
        return tankExplosionImage;
    }

    public BufferedImage getStarBonusImage() {
        return starBonusImage;
    }

    public BufferedImage getSpadeBonusImage() {
        return spadeBonusImage;
    }

    public BufferedImage getLifeBonusImage() {
        return lifeBonusImage;
    }

    public void writeErrorMessageToLog(Exception ex) {
        try {
            //TODO это как вообще?
            if (logFile == null)
                logFile.createNewFile();
            logWriter = new FileWriter(logFile);
            logWriter.write(ex.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                logWriter.flush();
                logWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage getPauseImage() {
        return pauseImage;
    }

    public BufferedImage getGameOverImage() {
        return gameOverImage;
    }

    public BufferedImage getYouWinImage() {
        return youWinImage;
    }

    public final void increaseCurrentLevelNum() {
        currentLevel++;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public final File getCurrentMapFile() {
        File currentMapFile = null;
        File mapsDir = new File("maps");
        if (mapsDir.isDirectory()) {
            //TODO проверить, как тут может быть NPE
            for (File file : mapsDir.listFiles()) {
                if (file.isFile()) {
                    StringBuilder nameWithNoExtension = new StringBuilder();
                    for (int i = 0; i < file.getName().length(); i++) {
                        char c = file.getName().charAt(i);
                        if (c != '.') {
                            nameWithNoExtension.append(c);
                        } else {
                            break;
                        }
                    }
                    // !! проверить, не может ли выброситься исключение
                    int num = Integer.parseInt(nameWithNoExtension.toString());
                    if (num == currentLevel) {
                        currentMapFile = file;
                        break;
                    }
                }
            }
        }
        return currentMapFile;
    }

    public static Image makeColorTransparent
            (Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ( ( rgb | 0xFF000000 ) == markerRGB ) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
