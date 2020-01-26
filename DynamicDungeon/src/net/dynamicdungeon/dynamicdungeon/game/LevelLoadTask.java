/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.game;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.ImageColorConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;

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
            final Application app = DynamicDungeon.getApplication();
            final Dungeon gameDungeon = app.getDungeonManager().getDungeon();
            app.getGameManager().disableEvents();
            gameDungeon.switchLevelOffset(this.level);
            gameDungeon.offsetPlayerLocationW(this.level);
            PartyManager.getParty().offsetDungeonLevel(this.level);
            AbstractDungeonObject
                    .setTemplateColor(ImageColorConstants.getColorForLevel(
                            PartyManager.getParty().getDungeonLevel()));
            app.getGameManager().updateStoneCount();
            app.getGameManager().resetViewingWindow();
            app.getGameManager().enableEvents();
            app.getGameManager().redrawDungeon();
        } catch (final Exception ex) {
            DynamicDungeon.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
