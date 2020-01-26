package com.puttysoftware.dungeondiver4.dungeon;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;

public class RandomDungeonGenerator extends Thread {
    @Override
    public void run() {
        try {
            final Application app = DungeonDiver4.getApplication();
            final JFrame generateFrame = new JFrame("Generating...");
            generateFrame.setIconImage(LogoManager.getIconLogo());
            final JProgressBar generateBar = new JProgressBar();
            generateBar.setIndeterminate(true);
            generateFrame.getContentPane().add(generateBar);
            generateFrame.setResizable(false);
            generateFrame.setDefaultCloseOperation(
                    WindowConstants.DO_NOTHING_ON_CLOSE);
            generateFrame.pack();
            generateFrame.setVisible(true);
            final Dungeon d = new Dungeon();
            d.addLevel(Dungeon.getMaxRows(), Dungeon.getMaxColumns(),
                    Dungeon.getMaxFloors());
            d.fillLevelRandomly();
            d.generateStart();
            d.save();
            generateFrame.setVisible(false);
            app.getDungeonManager().setDungeon(d);
            app.getDungeonManager().setLoaded(true);
            app.getGameManager().stateChanged();
            app.getGameManager().resetGameState();
            app.getGameManager().resetViewingWindowAndPlayerLocation();
            final boolean proceed = app.getGameManager().newGame();
            if (proceed) {
                app.getGameManager().playDungeon();
            }
        } catch (final Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
        }
    }
}
