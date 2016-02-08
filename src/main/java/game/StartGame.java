package game;

import game.gui.intro.IntroForm;
import game.logic.GameConfig;

import javax.swing.*;

public class StartGame {

    private StartGame() {
    }

    public static void main(final String[] args) {
        // эта строка нужна для победы над непонятным глюком!
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        GameConfig.getInstance();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IntroForm();
            }
        });
    }
}
