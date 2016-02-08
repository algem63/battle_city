package game.gui.bf;

import game.gui.common.BasicForm;
import game.gui.game.GameFieldPanel;
import game.gui.intro.IntroForm;
import game.logic.GameConfig;
import game.gui.game.MapLoader;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BattleField extends BasicForm {
    private final int FORM_WIDTH = 1000;
    private final int FORM_HEIGHT = 629;

    private GameFieldPanel gameFieldPanel;
    //TODO блок со статистикой можно вынести в отдельный класс
    private JLabel levelNum, levelValue, player1, player2,
            player1LifeValue, player2LifeValue,
            tanksKilled, statictics, name, player11, player22,
            shots, killed, lost, player1ShotsCount, player2ShotsCount,
            player1KilledCount, player2KilledCount,
            player1LostCount, player2LostCount,
            total, shots2, killed2, lost2, totalShotsCount1, totalShotsCount2,
            totalKilledCount1, totalKilledCount2,
            totalLostCount1, totalLostCount2;
    private JSeparator line, line2, line3, line4,
            line5 ,line6, line7, line8, line9, line10,
            line11, line12, line13, line14;
    private JLabel tanksKilledValue;

    public BattleField() throws HeadlessException {
        super();
        setTitle("Battle Field");
        setBounds(calculateXStartCoord(FORM_WIDTH), calculateYStartCoord(FORM_HEIGHT), FORM_WIDTH, FORM_HEIGHT);
        addComponents();
        repaint();
    }

    @Override
    public final void addComponents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameFieldPanel.stopAllTimersOnExit();
                gameFieldPanel.stopEnemyRespawner();
                GameConfig.getInstance().setCurrentLevel(1);
                new IntroForm();
            }
        });

        levelNum = new JLabel("LEVEL ");
        levelNum.setBounds(820, 3, 80, 30);
        levelNum.setFont(new Font("Times new roman", 1, 16));
        add(levelNum);

        levelValue = new JLabel();
        levelValue.setBounds(900, 3, 80, 30);
        levelValue.setFont(new Font("Times new roman", 1, 16));
        levelValue.setHorizontalAlignment(SwingConstants.CENTER);
        levelValue.setText("1");
        add(levelValue);

        line = new JSeparator();
        line.setBounds(810, 35, 175, 1);
        add(line);

        player1 = new JLabel("Player 1");
        player1.setFont(new Font("Times new roman", 1, 16));
        player1.setBounds(810, 38, 85, 33);
        add(player1);

        player1LifeValue = new JLabel("1");
        player1LifeValue.setHorizontalAlignment(SwingConstants.CENTER);
        player1LifeValue.setFont(new Font("Times new roman", 1, 16));
        player1LifeValue.setBounds(895, 38, 90, 33);
        add(player1LifeValue);

        player2 = new JLabel("Player 2");
        player2.setFont(new Font("Times new roman", 1, 16));
        player2.setBounds(810, 70, 85, 33);
        add(player2);

        line2 = new JSeparator();
        line2.setBounds(810, 109, 175, 1);
        add(line2);

        tanksKilled = new JLabel("Tanks killed:");
        tanksKilled.setFont(new Font("Times new roman", 1, 16));
        tanksKilled.setBounds(810, 105, 100, 40);
        add(tanksKilled);

//        tanksKilledValue = new JLabel("0/" + gameFieldPanel.getMaxTanksCount());
        tanksKilledValue = new JLabel("0/" + GameConfig.getInstance().getProperty("tanksCount"));

        tanksKilledValue.setFont(new Font("Times new roman", 1, 16));
        tanksKilledValue.setBounds(910, 105, 78, 40);
        tanksKilledValue.setHorizontalAlignment(SwingConstants.CENTER);
        add(tanksKilledValue);

        line3 = new JSeparator();
        line3.setBounds(810, 142, 175, 1);
        add(line3);

        statictics = new JLabel("Statistics");
        statictics.setFont(new Font("Times new roman", 1, 16));
        statictics.setBounds(855, 145, 150, 40);
        add(statictics);

        line4 = new JSeparator();
        line4.setBounds(810, 178, 175, 1);
        add(line4);

        name = new JLabel("Name");
        name.setFont(new Font("Times new roman", 1, 16));
        name.setBounds(810, 175, 40, 35);
        add(name);

        line5 = new JSeparator(JSeparator.VERTICAL);
        line5.setBounds(855, 180, 1, 25);
        add(line5);

        player11 = new JLabel("Player1");
        player11.setFont(new Font("Times new roman", 1, 16));
        player11.setBounds(860, 175, 60, 35);
        add(player11);

        line6 = new JSeparator(JSeparator.VERTICAL);
        line6.setBounds(920, 180, 1, 25);
        add(line6);

        player22 = new JLabel("Player2");
        player22.setFont(new Font("Times new roman", 1, 16));
        player22.setBounds(925, 175, 60, 35);
        add(player22);

        line7 = new JSeparator();
        line7.setBounds(810, 206, 175, 1);
        add(line7);

        shots = new JLabel("Shots");
        shots.setFont(new Font("Times new roman", 1, 16));
        shots.setBounds(810, 203, 40, 30);
        add(shots);

        killed = new JLabel("Killed");
        killed.setFont(new Font("Times new roman", 1, 16));
        killed.setBounds(810, 227, 40, 30);
        add(killed);

        lost = new JLabel("Lost");
        lost.setFont(new Font("Times new roman", 1, 16));
        lost.setBounds(810, 250, 40, 30);
        add(lost);

        line8 = new JSeparator();
        line8.setBounds(810, 280, 175, 1);
        add(line8);

        line9 = new JSeparator(JSeparator.VERTICAL);
        line9.setBounds(855, 208, 1, 71);
        add(line9);

        player1ShotsCount = new JLabel("0");
        player1ShotsCount.setHorizontalAlignment(SwingConstants.CENTER);
        player1ShotsCount.setFont(new Font("Times new roman", 1, 16));
        player1ShotsCount.setBounds(856, 204, 65, 30);
        add(player1ShotsCount);

        player1KilledCount = new JLabel("0");
        player1KilledCount.setHorizontalAlignment(SwingConstants.CENTER);
        player1KilledCount.setFont(new Font("Times new roman", 1, 16));
        player1KilledCount.setBounds(856, 227, 65, 30);
        add(player1KilledCount);

        player1LostCount = new JLabel("0");
        player1LostCount.setHorizontalAlignment(SwingConstants.CENTER);
        player1LostCount.setFont(new Font("Times new roman", 1, 16));
        player1LostCount.setBounds(856, 250, 65, 30);
        add(player1LostCount);

        line10 = new JSeparator(JSeparator.VERTICAL);
        line10.setBounds(920, 208, 1, 71);
        add(line10);

        player2ShotsCount = new JLabel("0");
        player2ShotsCount.setHorizontalAlignment(SwingConstants.CENTER);
        player2ShotsCount.setFont(new Font("Times new roman", 1, 16));
        player2ShotsCount.setBounds(921, 204, 65, 30);
        add(player2ShotsCount);

        player2KilledCount = new JLabel("0");
        player2KilledCount.setHorizontalAlignment(SwingConstants.CENTER);
        player2KilledCount.setFont(new Font("Times new roman", 1, 16));
        player2KilledCount.setBounds(921, 227, 65, 30);
        add(player2KilledCount);

        player2LostCount = new JLabel("0");
        player2LostCount.setHorizontalAlignment(SwingConstants.CENTER);
        player2LostCount.setFont(new Font("Times new roman", 1, 16));
        player2LostCount.setBounds(921, 250, 65, 30);
        add(player2LostCount);

        total = new JLabel("Total");
        total.setHorizontalAlignment(SwingConstants.CENTER);
        total.setFont(new Font("Times new roman", 1, 16));
        total.setBounds(856, 280, 65, 30);
        add(total);

        line11 = new JSeparator();
        line11.setBounds(810, 306, 175, 1);
        add(line11);

        shots2 = new JLabel("Shots");
        shots2.setFont(new Font("Times new roman", 1, 16));
        shots2.setBounds(810, 305, 40, 30);
        add(shots2);

        killed2 = new JLabel("Killed");
        killed2.setFont(new Font("Times new roman", 1, 16));
        killed2.setBounds(810, 330, 40, 30);
        add(killed2);

        lost2 = new JLabel("Lost");
        lost2.setFont(new Font("Times new roman", 1, 16));
        lost2.setBounds(810, 354, 40, 30);
        add(lost2);

        line12 = new JSeparator(JSeparator.VERTICAL);
        line12.setBounds(853, 309, 1, 71);
        add(line12);

        line13 = new JSeparator(JSeparator.VERTICAL);
        line13.setBounds(920, 309, 1, 71);
        add(line13);

        line14 = new JSeparator();
        line14.setBounds(810, 382, 175, 1);
        add(line14);

        totalShotsCount1 = new JLabel("0");
        totalShotsCount1.setHorizontalAlignment(SwingConstants.CENTER);
        totalShotsCount1.setFont(new Font("Times new roman", 1, 16));
        totalShotsCount1.setBounds(856, 310, 63, 22);
        add(totalShotsCount1);

        totalKilledCount1 = new JLabel("0");
        totalKilledCount1.setHorizontalAlignment(SwingConstants.CENTER);
        totalKilledCount1.setFont(new Font("Times new roman", 1, 16));
        totalKilledCount1.setBounds(856, 329, 65, 30);
        add(totalKilledCount1);

        totalLostCount1 = new JLabel("0");
        totalLostCount1.setHorizontalAlignment(SwingConstants.CENTER);
        totalLostCount1.setFont(new Font("Times new roman", 1, 16));
        totalLostCount1.setBounds(856, 354, 65, 30);
        add(totalLostCount1);

        totalShotsCount2 = new JLabel("0");
        totalShotsCount2.setHorizontalAlignment(SwingConstants.CENTER);
        totalShotsCount2.setFont(new Font("Times new roman", 1, 16));
        totalShotsCount2.setBounds(921, 305, 65, 30);
        add(totalShotsCount2);

        totalKilledCount2 = new JLabel("0");
        totalKilledCount2.setHorizontalAlignment(SwingConstants.CENTER);
        totalKilledCount2.setFont(new Font("Times new roman", 1, 16));
        totalKilledCount2.setBounds(921, 329, 65, 30);
        add(totalKilledCount2);

        totalLostCount2 = new JLabel("0");
        totalLostCount2.setHorizontalAlignment(SwingConstants.CENTER);
        totalLostCount2.setFont(new Font("Times new roman", 1, 16));
        totalLostCount2.setBounds(921, 354, 65, 30);
        add(totalLostCount2);

        gameFieldPanel = new GameFieldPanel(this);
        add(gameFieldPanel);
        
        new MapLoader(gameFieldPanel, GameConfig.getInstance().getCurrentMapFile()).start();
    }

    public final void increaseShotsCountForPlayerOne() {
        int count = Integer.parseInt(player1ShotsCount.getText());
        count++;
        player1ShotsCount.setText(String.valueOf(count));
    }

    public final void increaseTotalShotsCountForPlayerOne() {
        int count = Integer.parseInt(totalShotsCount1.getText());
        count++;
        totalShotsCount1.setText(String.valueOf(count));
    }
    
    public final void increaseTotalKilledCountForPlayerOne() {
        int count = Integer.parseInt(totalKilledCount1.getText());
        count++;
        totalKilledCount1.setText(String.valueOf(count));
    }

    public final void increaseTanksKilled() {
        StringBuilder newValue = new StringBuilder();
        String oldValue = tanksKilledValue.getText();
        for (int i = 0; i < oldValue.length(); i++) {
            char c = oldValue.charAt(i);
            if (c != '/') {
                newValue.append(c);
            } else {
                break;
            }
        }
        int newVal = Integer.parseInt(newValue.toString());
        newVal++;
        tanksKilledValue.setText(String.valueOf(newVal)
                + "/"
                + GameConfig.getInstance().getProperty("tanksCount"));
    }

    // TODO быдлокодный метод. Убрать.
    public final boolean isAllTanksKilled() {
        int i = 0;
        String text = tanksKilledValue.getText();
        for (; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                break;
            }
        }
        int tanksKilled = Integer.parseInt(text.substring(0, i));
        int tanksLeft = Integer.parseInt(text.substring(i + 1, text.length()));
        if (tanksKilled == tanksLeft) {
            return true;
        } else {
            return false;
        }
    }

    public final void increasePlayerOneKilledCount() {
        int count = Integer.parseInt(player1KilledCount.getText());
        count++;
        player1KilledCount.setText(String.valueOf(count));
    }

    public final void setStaticticsToZero() {
        if (tanksKilledValue != null) {
            tanksKilledValue.setText("0/"
                    + GameConfig.getInstance().getProperty("tanksCount"));
        }
        if (player1KilledCount != null) {
            player1KilledCount.setText("0");
        }
        if (player1ShotsCount != null) {
            player1ShotsCount.setText("0");
        }

    }

    public final void setTotalStatiscticsToZero() {
        if (totalShotsCount1 != null) {
            totalShotsCount1.setText("0");
        }
        if (totalKilledCount1 != null) {
            totalKilledCount1.setText("0");
        }
    }

    public final void setPlayerOneLifeValue(final int value) {
        player1LifeValue.setText(String.valueOf(value));
    }
    
    public final void decreasePlayerOneLifeValue() {
        int value = Integer.parseInt(player1LifeValue.getText());
        value--;
        player1LifeValue.setText(String.valueOf(value));
    }

    public void setLevelValue(String value){
        levelValue.setText(value);
    }
}
