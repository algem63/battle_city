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

public class GameConfig {
    private static GameConfig instance = null;

    private Properties gameConfig;
    private File configFile;
    private InputStream propertyReader;
    private FileOutputStream configWriter;
    private Toolkit kit;

    private File logFile;
    private FileWriter logWriter;

    private BufferedImage headquartersImageL1, headquartersImageL2, headquartersImageL3, headquartersImageL4, headquartersImageL5, headquartersImageL6, headquartersImageL7;
    private BufferedImage brickImage;
    private BufferedImage concreteImage;
    private BufferedImage waterImage;
    private BufferedImage tankImage;
    private BufferedImage playerOneTankImage;
    private BufferedImage playerTwoTankImage, pauseImage,
            gameOverImage, youWinImage;
    private BufferedImage starBonusImage,
            lifeBonusImage, spadeBonusImage;
    private Image explosionImage;
    private Image tankExplosionImage;

    int currentLevel;

    private GameConfig() {
        // загружаем настройки
        configFile = new File(Constants.configFile);
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
    
    public String getProperty(final String key) {
        return gameConfig.getProperty(key);
    }

    public void setProperty(final String prop, final String value) {
        gameConfig.setProperty(prop, value);
    }

    public void createDefaultConfig() {
        try {
            logFile = new File("error.log");
            propertyReader = getClass().getResourceAsStream(Constants.defaultConfig);
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

    public void loadConfig() {
        try {
            propertyReader = new FileInputStream(Constants.configFile);
            gameConfig = new Properties();
            gameConfig.load(propertyReader);
        } catch (FileNotFoundException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
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

    public void saveConfig() {
        try {
            configWriter = new FileOutputStream(Constants.configFile);
            gameConfig.store(configWriter, null);
        } catch (FileNotFoundException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } finally {
            try {
                configWriter.close();
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            }
        }
    }

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
        if (gameConfig.containsValue(String.valueOf(value))) {
            return true;
        } else {
            return false;
        }
    }

    public void loadGraphics() {
        kit = Toolkit.getDefaultToolkit();
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
//            explosionImage = ImageIO.read(getClass().getResource("/images/Explosion.gif"));
//            tankExplosionImage = ImageIO.read(getClass().getResource("/images/TankExplosion.gif"));
            pauseImage = ImageIO.read(getClass().getResource(Constants.pauseImage));
            gameOverImage = ImageIO.read(getClass().getResource(Constants.gameOverImage));
            lifeBonusImage = ImageIO.read(getClass().getResource(Constants.lifeBonusImage));
            spadeBonusImage = ImageIO.read(getClass().getResource(Constants.spadeBonusImage));
            starBonusImage = ImageIO.read(getClass().getResource(Constants.starBonusImage));
            youWinImage = ImageIO.read(getClass().getResource(Constants.youWinImage));
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        }

//        makeColorTransparent(explosionImage, new Color(Constants.BLACK_BACKGROUND));

        /*for (int y = 0; y < explosionImage.getHeight(); ++y) {
            for (int x = 0; x < explosionImage.getWidth(); ++x) {
                // делаем черный цвет прозрачным
                int argb = explosionImage.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    explosionImage.setRGB(x, y, 0);
                }
            }
        }*/
        /*for (int y = 0; y < tankExplosionImage.getHeight(); ++y) {
            for (int x = 0; x < tankExplosionImage.getWidth(); ++x) {
                // делаем черный цвет прозрачным
                int argb = tankExplosionImage.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    tankExplosionImage.setRGB(x, y, 0);
                }
            }
        }*/
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
