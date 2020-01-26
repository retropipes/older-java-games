/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.ImageColorConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class GenerateTask extends Thread {
    // Fields
    private final JFrame generateFrame;
    private final boolean scratch;

    // Constructors
    public GenerateTask(final boolean startFromScratch) {
        this.scratch = startFromScratch;
        this.setName("Level Generator");
        this.generateFrame = new JFrame("Generating...");
        this.generateFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.generateFrame.getContentPane().add(loadBar);
        this.generateFrame.setResizable(false);
        this.generateFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.generateFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        try {
            this.generateFrame.setVisible(true);
            final Application app = DynamicDungeon.getApplication();
            Dungeon gameDungeon = app.getDungeonManager().getDungeon();
            if (!this.scratch) {
                app.getGameManager().disableEvents();
            } else {
                gameDungeon = new Dungeon();
                app.getDungeonManager().setDungeon(gameDungeon);
            }
            final int levelCount = gameDungeon.getLevels();
            final int mazeSize = DungeonConstants.MAZE_SIZE_BASE
                    + levelCount * DungeonConstants.MAZE_SIZE_INCREMENT;
            gameDungeon.addLevel(mazeSize, mazeSize, Dungeon.getMaxFloors());
            gameDungeon.fillLevelRandomly();
            final RandomRange rR = new RandomRange(0, mazeSize - 1);
            final RandomRange rC = new RandomRange(0, mazeSize - 1);
            final RandomRange rF = new RandomRange(0,
                    Dungeon.getMaxFloors() - 1);
            if (this.scratch) {
                int startR, startC, startF;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                    startF = rF.generate();
                } while (gameDungeon.getCell(startR, startC, startF,
                        DungeonConstants.LAYER_OBJECT).isSolid());
                gameDungeon.setStartRow(startR);
                gameDungeon.setStartColumn(startC);
                gameDungeon.setStartFloor(startF);
                app.getDungeonManager().setLoaded(true);
                final boolean playerExists = gameDungeon.doesPlayerExist();
                if (playerExists) {
                    gameDungeon.setPlayerToStart();
                    app.getGameManager().resetViewingWindow();
                }
            } else {
                int startR, startC, startF;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                    startF = rF.generate();
                } while (gameDungeon.getCell(startR, startC, startF,
                        DungeonConstants.LAYER_OBJECT).isSolid());
                gameDungeon.setPlayerLocationX(startR);
                gameDungeon.setPlayerLocationY(startC);
                gameDungeon.setPlayerLocationZ(startF);
                PartyManager.getParty().offsetDungeonLevel(1);
            }
            gameDungeon.save();
            // Final cleanup
            app.getGameManager().updateStoneCount();
            AbstractDungeonObject
                    .setTemplateColor(ImageColorConstants.getColorForLevel(
                            PartyManager.getParty().getDungeonLevel()));
            if (this.scratch) {
                app.getGameManager().stateChanged();
                app.getGameManager().playDungeon();
            } else {
                app.getGameManager().resetViewingWindow();
                app.getGameManager().enableEvents();
                app.getGameManager().redrawDungeon();
            }
        } catch (final Throwable t) {
            DynamicDungeon.getErrorLogger().logError(t);
        } finally {
            this.generateFrame.setVisible(false);
        }
    }
}
