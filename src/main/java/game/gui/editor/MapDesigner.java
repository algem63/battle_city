package game.gui.editor;

import game.gui.common.BasicForm;
import game.gui.game.GameFieldPanel;
import game.gui.intro.IntroForm;
import game.logic.GameConfig;
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
import java.awt.event.*;
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
        setBounds(calculateXStartCoord(FORM_WIDTH), calculateYStartCoord(FORM_HEIGHT), FORM_WIDTH, FORM_HEIGHT);
        addComponents();        
        repaint();
    }

    @Override
    public final void addComponents() {
        //TODO тулбар сделать отдельным классом
        // открытие стартовой формы после нажатия на крестик
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new IntroForm();
            }
        });

        /*
        addNewButton();
        addOkButton();
        addSaveButton();
        addCloseButton();
        addSeparator();

        brickIcon = GameConfig.getInstance().getBrickImage();
        concreteIcon = GameConfig.getInstance().getConcreteImage();
        waterIcon = GameConfig.getInstance().getWaterImage();
        headquartersIcon = GameConfig.getInstance().getHeadquartersImageL1();
*/
        addStatusBar();

        /*
        addBrickButton();
        addConcreteButton();
        addWaterButton();
        addHQButton();
        addToolBar();
*/

        gameFieldPanel = new GameFieldPanel(statusBar);
        gameFieldPanel.setBounds(3, 62, 800, 600);
        add(gameFieldPanel);

        add(new ToolBar(gameFieldPanel, this));
    }

    private void addToolBar() {
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
    }

    private void addHQButton() {
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
    }

    private void addWaterButton() {
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
    }

    private void addConcreteButton() {
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
    }

    private void addBrickButton() {
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
    }

    private void addStatusBar() {
        statusBar = new StatusBar();
        statusBar.setBounds(0, 668, FORM_WIDTH, 36);
        add(statusBar);
    }

    private void addSeparator() {
        vertical = new JSeparator();
        // вертикальное расположение сепаратора
        vertical.setOrientation(1);
        vertical.setBounds(225, 5, 1, 46);
    }

    private void addCloseButton() {
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
    }

    private void addSaveButton() {
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
    }

    private void addOkButton() {
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
    }

    private void addNewButton() {
        nButton = new JButton("N");
        nButton.setBounds(10, 10, 45, 37);
        nButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                gameFieldPanel.removeAllComponents();
            }
        });
    }

    //TODO разбить на методы
    public final void saveMapFile() {
        FileOutputStream saveMapStream = null;
        ObjectOutputStream saveMapStream2 = null;
        fileDialog = new JFileChooser();
        fileDialog.setCurrentDirectory(new File("."));
        fileDialog.setFileFilter(new MapFileFilter("map", "Game map files"));
        int result = fileDialog.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File mapFile = fileDialog.getSelectedFile();
            String pathToMapFile = mapFile.getAbsolutePath();
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
                Component[] components = gameFieldPanel.getComponents();
                Integer count = Integer.valueOf(components.length);
                //TODO зачем количество объектов записывается в файл?
                saveMapStream2.writeObject(count);
                for (Component component: components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        saveMapStream2.writeObject(mapObject);
                    }
                }
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } finally {
                try {
                    if (saveMapStream2 != null) {
                        saveMapStream2.close();
                    }
                    if (saveMapStream != null) {
                        saveMapStream.close();
                    }
                } catch (IOException e) {
                    GameConfig.getInstance().writeErrorMessageToLog(e);
                }
            }
        }
    }

    public final void openMapFile() {
        fileDialog = new JFileChooser();
        fileDialog.setCurrentDirectory(new File("."));
        fileDialog.setFileFilter(new MapFileFilter("map", "Game map files"));
        int result = fileDialog.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File mapFile = fileDialog.getSelectedFile();
            FileInputStream loadMapStream = null;
            ObjectInputStream loadMapStream2 = null;
            try {
                loadMapStream = new FileInputStream(mapFile);
                loadMapStream2 = new ObjectInputStream(loadMapStream);//
                gameFieldPanel.removeAllComponents();
                MapObject mapObject;
                Object obj;
                while ((obj =  loadMapStream2.readObject()) != null) {
                    if (obj instanceof Integer) continue;
                    mapObject = (MapObject) obj;
                    gameFieldPanel.addMapObject(mapObject.getX(),
                            mapObject.getY(),
                            mapObject.getImageNum(),
                            mapObject.getObjectSize());
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                GameConfig.getInstance().writeErrorMessageToLog(e);
            } finally {
                try {
                    if (loadMapStream2 != null) {
                        loadMapStream2.close();
                    }
                    if (loadMapStream != null) {
                        loadMapStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            gameFieldPanel.repaint();
        }
    }
}
