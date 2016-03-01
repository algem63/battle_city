package game.gui.editor;

import game.gui.game.GameFieldPanel;
import game.gui.intro.IntroForm;
import game.logic.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by melnikov on 11.02.16.
 */
public class ToolBar extends JPanel {

    private GameFieldPanel gameFieldPanel;
    private MapDesigner mapDesigner;

    private JPanel toolBar;

    private JButton nButton;
    private JButton oButton;
    private JButton sButton;
    private JButton qButton;

    private JToggleButton brickButton;
    private JToggleButton concreteButton;
    private JToggleButton waterButton;
    private JToggleButton headquartersButton;

    private BufferedImage brickIcon = GameConfig.getInstance().getBrickImage();
    private BufferedImage concreteIcon = GameConfig.getInstance().getConcreteImage();
    private BufferedImage waterIcon = GameConfig.getInstance().getWaterImage();
    private BufferedImage headquartersIcon = GameConfig.getInstance().getHeadquartersImageL1();

    public static boolean brickSelected;
    public static boolean concreteSelected;
    public static boolean waterSelected;
    public static boolean headQuartersSelected;

    public ToolBar(GameFieldPanel gameFieldPanel, MapDesigner mapDesigner) {
        this.gameFieldPanel = gameFieldPanel;
        this.mapDesigner = mapDesigner;

        //TODO make constants!
        setBounds(0, 0, 812, 57);
        setBorder(BorderFactory.createEtchedBorder());
        //TODO change layout
        setLayout(null);

        addNewButton();
        add(nButton);
        addOkButton();
        add(oButton);
        addSaveButton();
        add(sButton);
        addCloseButton();
        add(qButton);

        addBrickButton();
        add(brickButton);
        addConcreteButton();
        add(concreteButton);
        addWaterButton();
        add(waterButton);
        addHQButton();
        add(headquartersButton);
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
                mapDesigner.openMapFile();
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
                //TODO вероятно, процедуру удаления следует проводить при загрузке карты, а не при сохранении
                gameFieldPanel.freeRespawningZones();
                mapDesigner.saveMapFile();
            }
        });
    }

    private void addCloseButton() {
        qButton = new JButton("Q");
        qButton.setBounds(170, 10, 45, 37);
        qButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                MapDesigner.brickSelected = false;
                MapDesigner.concreteSelected = false;
                MapDesigner.waterSelected = false;
                MapDesigner.headQuartersSelected = false;

                mapDesigner.dispose();
                IntroForm introForm = new IntroForm();
                introForm.setVisible(true);
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
}
