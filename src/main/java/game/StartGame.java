package game;

import game.gui.intro.IntroForm;
import game.logic.GameConfig;

public class StartGame {

    private StartGame() {
    }

    public static void main(final String[] args) {
        // эта строка нужна для победы над непонятным глюком!
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        GameConfig gc = GameConfig.getInstance();
        IntroForm introForm = new IntroForm();
    }
}
