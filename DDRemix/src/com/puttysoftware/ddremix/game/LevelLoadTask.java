/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.game;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.creatures.party.PartyManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractMazeObject;
import com.puttysoftware.ddremix.maze.utilities.ImageColorConstants;
import com.puttysoftware.ddremix.resourcemanagers.LogoManager;

public class LevelLoadTask extends Thread {
    // Fields
    private final JFrame loadFrame;
    private final int level;

    // Constructors
    public LevelLoadTask(final int offset) {
        this.level = offset;
        this.setName("Level Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        try {
            this.loadFrame.setVisible(true);
            final Application app = DDRemix.getApplication();
            final Maze gameMaze = app.getMazeManager().getMaze();
            app.getGameManager().disableEvents();
            gameMaze.switchLevelOffset(this.level);
            gameMaze.offsetPlayerLocationW(this.level);
            PartyManager.getParty().offsetDungeonLevel(this.level);
            AbstractMazeObject
                    .setTemplateColor(ImageColorConstants
                            .getColorForLevel(PartyManager.getParty()
                                    .getDungeonLevel()));
            app.getGameManager().updateStoneCount();
            app.getGameManager().resetViewingWindow();
            app.getGameManager().enableEvents();
            app.getGameManager().redrawMaze();
        } catch (final Exception ex) {
            DDRemix.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
