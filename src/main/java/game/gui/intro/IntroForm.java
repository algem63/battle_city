package game.gui.intro;

import game.gui.*;

import javax.swing.JButton;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroForm extends BasicForm {

    private TankPictureLoader tankPictureLoader;
    private JButton onePlayer;
    private JButton twoPlayers;
    private JButton construction;
    private JButton options;
    private JButton exit;

    private final int FORM_WIDTH = 645;
    private final int FORM_HEIGHT = 321;

    public IntroForm() throws HeadlessException {
       super();
       setTitle("TANKS");
       setBounds(calculateXStartCoord(FORM_WIDTH),
                 calculateYStartCoord(FORM_HEIGHT),
               FORM_WIDTH,
               FORM_HEIGHT);
       addComponents();
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       repaint();
    }    
    
    //TODO ���������� ��������� ������� � ��������� ��������� �����
    @Override
    public final void addComponents() {
        tankPictureLoader = new TankPictureLoader();
        tankPictureLoader.setBounds(0, 0, 430, 321);
        add(tankPictureLoader);

        onePlayer = new JButton("One Player");
        onePlayer.setBounds(433, 8, 200, 45);
        onePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                BasicForm battleField = new BattleField();
                dispose();
            }
        });
        add(onePlayer);

        twoPlayers = new JButton("Two Players");
        twoPlayers.setBounds(433, 66, 200, 45);
        add(twoPlayers);

        construction = new JButton("Construction");
        construction.setBounds(433, 124, 200, 45);
        construction.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                MapDesigner mapDesigner = new MapDesigner();
                dispose();
            }
        });
        add(construction);

        options = new JButton("Options");
        options.setBounds(433, 182, 200, 45);
        options.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                OptionsForm optionsForm = new OptionsForm();
                dispose();
            }
        });
        add(options);

        exit = new JButton("Exit");
        exit.setBounds(433, 240, 200, 45);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        add(exit);
    }
}
