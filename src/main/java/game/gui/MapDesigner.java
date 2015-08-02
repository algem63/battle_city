package game.gui;

import game.gui.intro.IntroForm;
import game.logic.GameConfig;
import game.logic.MapFileFilter;
import game.objects.MapObject;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 04.06.12
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public class MapDesigner extends BasicForm {
    private final int FORM_WIDTH = 812;
    private final int FORM_HEIGHT = 731;

    private JPanel toolBar;
    private JButton nButton;
    private JButton oButton;
    private JButton sButton;
    private JButton qButton;
    private JToggleButton brickButton;
    private JToggleButton concreteButton;
    private JToggleButton waterButton;
    private JToggleButton headquartersButton;
    private JSeparator vertical;
    private JFileChooser fileDialog;

    private BufferedImage brickIcon;
    private BufferedImage concreteIcon;
    private BufferedImage waterIcon;
    private BufferedImage headquartersIcon;
    private GameFieldPanel gameFieldPanel;
    private StatusBar statusBar;

    public static boolean brickSelected;
    public static boolean concreteSelected;
    public static boolean waterSelected;
    public static boolean headQuartersSelected;

    public MapDesigner() throws HeadlessException {
        super();
        setTitle("Map Designer");
        setBounds(calculateXStartCoord(FORM_WIDTH),
                  calculateYStartCoord(FORM_HEIGHT),
                FORM_WIDTH,
                FORM_HEIGHT);
        addComponents();        
        repaint();
    }

    @Override
    public final void addComponents() {
        //TODO заменить на адаптер
        //TODO тулбар сделать отдельным классом
        // открытие стартовой формы после нажатия на крестик
        addWindowListener(new WindowListener() {
            public void windowOpened(final WindowEvent e) {
            }

            public void windowClosing(final WindowEvent e) {
                new IntroForm();
            }

            public void windowClosed(final WindowEvent e) {
            }

            public void windowIconified(final WindowEvent e) {
            }

            public void windowDeiconified(final WindowEvent e) {
            }

            public void windowActivated(final WindowEvent e) {
            }

            public void windowDeactivated(final WindowEvent e) {
            }
        });

        // Кнопка N удаляет все компоненты с поля
        nButton = new JButton("N");
        nButton.setBounds(10, 10, 45, 37);
        nButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                gameFieldPanel.removeAllComponents();
            }
        });

        oButton = new JButton("O");
        oButton.setBounds(60, 10, 45, 37);
        oButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // При нажатии на кнопку открытия файла
                // кнопки с инструментами становятся неактивными
                brickButton.setSelected(false);
                concreteButton.setSelected(false);
                waterButton.setSelected(false);
                headquartersButton.setSelected(false);
                openMapFile();
            }
        });

        sButton = new JButton("S");
        sButton.setBounds(110, 10, 45, 37);
        sButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // При нажатии на кнопку сохранения файла
                // кнопки с инструментами становятся неактивными
                brickButton.setSelected(false);
                concreteButton.setSelected(false);
                waterButton.setSelected(false);
                headquartersButton.setSelected(false);
                // удаляем объекты в местах респавна
                gameFieldPanel.freeRespawningZones();
                saveMapFile();
            }
        });

        qButton = new JButton("Q");
        qButton.setBounds(170, 10, 45, 37);
        qButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                brickSelected = false;
                concreteSelected = false;
                waterSelected = false;
                headQuartersSelected = false;

                dispose();
                IntroForm introForm = new IntroForm();
                introForm.setVisible(true);
            }
        });
        vertical = new JSeparator();
        // вертикальное расположение сепаратора
        vertical.setOrientation(1);
        vertical.setBounds(225, 5, 1, 46);

        brickIcon = GameConfig.getInstance().getBrickImage();
        concreteIcon = GameConfig.getInstance().getConcreteImage();
        waterIcon = GameConfig.getInstance().getWaterImage();
        headquartersIcon = GameConfig.getInstance().getHeadquartersImageL1();

        statusBar = new StatusBar();
        statusBar.setBounds(0, 668, FORM_WIDTH, 36);
        add(statusBar);

        brickButton = new JToggleButton();
        brickButton.setBounds(235, 10, 45, 37);
        brickButton.setIcon(new ImageIcon(brickIcon));
        brickButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (brickButton.isSelected()) {
                    brickSelected = true;
                    concreteSelected = false;
                    waterSelected = false;
                    headQuartersSelected = false;
                    concreteButton.setSelected(false);
                    waterButton.setSelected(false);
                    headquartersButton.setSelected(false);
                } else {
                    brickSelected = false;
                }
            }
        });

        concreteButton = new JToggleButton();
        concreteButton.setBounds(290, 10, 45, 37);
        concreteButton.setIcon(new ImageIcon(concreteIcon));
        concreteButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (concreteButton.isSelected()) {
                    concreteSelected = true;
                    brickSelected = false;
                    waterSelected = false;
                    headQuartersSelected = false;
                    brickButton.setSelected(false);
                    waterButton.setSelected(false);
                    headquartersButton.setSelected(false);
                } else {
                    concreteSelected = false;
                }
            }
        });

        waterButton = new JToggleButton();
        waterButton.setBounds(345, 10, 45, 37);
        waterButton.setIcon(new ImageIcon(waterIcon));
        waterButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                concreteSelected = false;
                brickSelected = false;
                waterSelected = true;
                headQuartersSelected = false;
                brickButton.setSelected(false);
                concreteButton.setSelected(false);
                headquartersButton.setSelected(false);
            }
        });

        headquartersButton = new JToggleButton();
        headquartersButton.setBounds(400, 6, 45, 45);
        headquartersButton.setIcon(new ImageIcon(headquartersIcon));
        headquartersButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                concreteSelected = false;
                brickSelected = false;
                waterSelected = false;
                headQuartersSelected = true;
                brickButton.setSelected(false);
                concreteButton.setSelected(false);
                waterButton.setSelected(false);
            }
        });

        toolBar = new JPanel();
        toolBar.setBounds(0, 0, FORM_WIDTH, 57);
        toolBar.setBorder(BorderFactory.createEtchedBorder());
        toolBar.setLayout(null);
        toolBar.add(nButton);
        toolBar.add(oButton);
        toolBar.add(sButton);
        toolBar.add(qButton);
        toolBar.add(vertical);
        toolBar.add(brickButton);
        toolBar.add(concreteButton);
        toolBar.add(waterButton);
        toolBar.add(headquartersButton);
        add(toolBar);

        gameFieldPanel = new GameFieldPanel(statusBar);
        gameFieldPanel.setBounds(3, 62, 800, 600);
        add(gameFieldPanel);
        
        /*for (int i = 0; i < gameFieldPanel.getWidth(); i+= Constants.brickSize) {
            for (int j = 0; j < gameFieldPanel.getHeight(); j+= Constants.brickSize) {
                gameFieldPanel.addMapObject(i, j, 1, Constants.brickSize);
            }
        }
        gameFieldPanel.repaint();*/
    }

    //TODO разбить на методы
    public final void saveMapFile() {
        FileOutputStream saveMapStream = null;
        ObjectOutputStream saveMapStream2 = null;

        fileDialog = new JFileChooser();
        // устанавливаем текущую директорию для выбора файлов
        fileDialog.setCurrentDirectory(new File("."));
        fileDialog.setFileFilter(new MapFileFilter("map", "Game map files"));
        int result = fileDialog.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File mapFile = fileDialog.getSelectedFile();
            String pathToMapFile = mapFile.getAbsolutePath();
            // добавляем расширение к файлу
            if (!pathToMapFile.endsWith(".map")) {
                mapFile = new File(pathToMapFile + ".map");
            }
            // проверяем, существует ли такой файл.
            // Если да - выводим предупреждение.
            if (!mapFile.exists()) {
                try {
                    mapFile.createNewFile();
                } catch (IOException e) {
                    GameConfig.getInstance().writeErrorMessageToLog(e);
                }
            } else {
                int i = JOptionPane.showConfirmDialog(this,
                        "File " + fileDialog.getSelectedFile().getName() + " already exists. Overwrite?");
                if (i == 0) {
                    try {
                        mapFile.createNewFile();
                    } catch (IOException e) {
                        GameConfig.getInstance().writeErrorMessageToLog(e);
                    }
                }
            }
            try {
                saveMapStream = new FileOutputStream(mapFile);
                saveMapStream2 = new ObjectOutputStream(saveMapStream);
//                старая версия
//                saveMapStream2.writeObject(components);

                // новая версия
//                Component[] components = gameFieldPanel.getComponents();
//                for (Component component: components) {
//                    MapObject mapObject = (MapObject) component;
//                    saveMapStream2.writeObject(mapObject);
//                }

                // самая новая версия
//                List<MapObject> mapObjects = new ArrayList<MapObject>();
                Component[] components = gameFieldPanel.getComponents();
                Integer count = Integer.valueOf(components.length);
                saveMapStream2.writeObject(count);

                for (Component component: components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        saveMapStream2.writeObject(mapObject);
//                        mapObjects.add((MapObject) component);
                    }
                }

//                saveMapStream2.writeObject(mapObjects);
            } catch (FileNotFoundException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } finally {
                try {
                    saveMapStream2.close();
                    saveMapStream.close();
                } catch (IOException e) {
                    GameConfig.getInstance().writeErrorMessageToLog(e);
                }
            }
        }
    }

    public final void openMapFile() {
        fileDialog = new JFileChooser();
        // устанавливаем текущую директорию для выбора файлов
        fileDialog.setCurrentDirectory(new File("."));
        fileDialog.setFileFilter(new MapFileFilter("map", "Game map files"));

        int result = fileDialog.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File mapFile = fileDialog.getSelectedFile();
            Component[] components = null;
            FileInputStream loadMapStream = null;
            ObjectInputStream loadMapStream2 = null;
//            List<MapObject> mapObjects = null;
            try {
                loadMapStream = new FileInputStream(mapFile);
                loadMapStream2 = new ObjectInputStream(loadMapStream);
//              Старый вариант
//                components = (Component[]) loadMapStream2.readObject();

//              новый вариант
                gameFieldPanel.removeAllComponents();
                MapObject mapObject = null;
                while ((mapObject = (MapObject) loadMapStream2.readObject()) != null) {
                    gameFieldPanel.addMapObject(mapObject.getX(),
                            mapObject.getY(),
                            mapObject.getImageNum(),
                            mapObject.getObjectSize());
                }

//                mapObjects = (List<MapObject>) loadMapStream2.readObject();
            // EOFException наследуется от IOException,
            // поэтому должно обрабатываться раньше
            } catch (EOFException e) {

            } catch (FileNotFoundException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } catch (IOException e) {

//                GameConfig.getInstance().writeErrorMessageToLog(e);
            } catch (ClassNotFoundException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } finally {
                try {
                    loadMapStream2.close();
                    loadMapStream.close();
                } catch (IOException e) {
                    // !! возможно, обработчик надо переделать
                    e.printStackTrace();
                }
            }
            /*for (MapObject mapObject: mapObjects) {
                gameFieldPanel.addMapObject(mapObject.getX(),
                        mapObject.getY(),
                        mapObject.getImageNum(),
                        mapObject.getObjectSize());
            }*/
            // Удаляем все компоненты формы и заполняем ее считанными из файла.
            // Сохранения при этом не происходит, надо с этим что-то сделать! :-))


//          старый вариант
//          gameFieldPanel.removeAllComponents();
            /*for (Component component: components) {
                if (component != null) {
                    MapObject mapObject = (MapObject) component;
                    gameFieldPanel.addMapObject(mapObject.getX(),
                            mapObject.getY(),
                            mapObject.getImageNum(),
                            mapObject.getObjectSize());
                }
            }*/
            gameFieldPanel.repaint();
        }
    }
}
