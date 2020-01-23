package net.worldwizard.dungeondiver.dungeon;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.creatures.Battle;
import net.worldwizard.dungeondiver.creatures.Stats;
import net.worldwizard.dungeondiver.dungeon.buffs.DungeonBuffManager;
import net.worldwizard.dungeondiver.dungeon.objects.DungeonObject;
import net.worldwizard.dungeondiver.dungeon.objects.DungeonObjectList;
import net.worldwizard.dungeondiver.dungeon.objects.Monster;
import net.worldwizard.dungeondiver.dungeon.objects.Tile;
import net.worldwizard.map.MapObject;
import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.map.NDimensionalMap;

public class DungeonGUI {
    // Fields
    private final JFrame outputFrame, generatorFrame, statsFrame;
    private Container outputPane, effectsPane;
    private final Container borderPane;
    private final Container statsPane;
    private final JLabel[] stats;
    private final JProgressBar generatorProgress;
    private DungeonMap dungeon;
    private DungeonObject savedMapObject;
    private final DungeonGUIEventHandler handler;
    private NDimensionalLocation playerLocation;
    private Thread generatorTask;
    private final DungeonObjectList objectList;
    private int moveX, moveY;
    private final DungeonBuffManager buffMgr;
    private final Dimension viewingWindow;
    public static final int VIEWING_WINDOW_SIZE = 11;
    private static final int MAX_DRAW_DISTANCE = DungeonGUI.VIEWING_WINDOW_SIZE / 2 + 1;
    private static final int MIN_DRAW_DISTANCE = 2;
    private static final int DEFAULT_DRAW_DISTANCE = 4;
    private static final int DUNGEON_SIZE = 30;
    private static final int DUNGEON_SIZE_INCREMENT = 2;

    // Constructors
    public DungeonGUI() {
        this.handler = new DungeonGUIEventHandler();
        this.outputFrame = new JFrame("Dungeon");
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputFrame.setResizable(false);
        this.outputFrame.addWindowListener(this.handler);
        this.outputFrame.addKeyListener(this.handler);
        this.generatorFrame = new JFrame("Generating...");
        this.generatorProgress = new JProgressBar();
        this.generatorProgress.setIndeterminate(true);
        this.generatorFrame.getContentPane().add(this.generatorProgress);
        this.generatorFrame.setResizable(false);
        this.generatorFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.objectList = new DungeonObjectList();
        this.savedMapObject = (Tile) DungeonObjectList
                .getSpecificObject("Tile");
        this.buffMgr = new DungeonBuffManager();
        this.outputPane = new Container();
        this.borderPane = new Container();
        this.effectsPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
        this.outputFrame.setContentPane(this.borderPane);
        this.statsFrame = new JFrame("Stats");
        this.statsFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.statsFrame.setResizable(false);
        this.statsFrame.setFocusableWindowState(false);
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(Stats.MAX_STATS, 1));
        this.stats = new JLabel[Stats.MAX_STATS];
        int x;
        for (x = 0; x < Stats.MAX_STATS; x++) {
            this.stats[x] = new JLabel("");
            this.stats[x].setOpaque(true);
            this.statsPane.add(this.stats[x]);
        }
        this.statsFrame.setContentPane(this.statsPane);
        this.statsFrame.setLocationRelativeTo(this.outputFrame);
        this.viewingWindow = new Dimension(DungeonGUI.VIEWING_WINDOW_SIZE,
                DungeonGUI.VIEWING_WINDOW_SIZE);
    }

    // Methods
    public DungeonBuffManager getBuffManager() {
        return this.buffMgr;
    }

    public DungeonObject getSavedMapObject() {
        return this.savedMapObject;
    }

    public void setSavedMapObject(final DungeonObject newSaved) {
        this.savedMapObject = newSaved;
    }

    public DungeonMap getDungeon() {
        return this.dungeon;
    }

    public void setDungeon(final DungeonMap newDungeon) {
        this.dungeon = newDungeon;
    }

    public JFrame getGenerator() {
        return this.generatorFrame;
    }

    public DungeonObjectList getObjectList() {
        return this.objectList;
    }

    public void updatePosition(final int x, final int y) {
        int move = this.buffMgr.encodeMovement(x, y);
        move = this.buffMgr.modifyMove(move);
        final int[] modMove = this.buffMgr.decodeMovement(move);
        this.moveX = modMove[0];
        this.moveY = modMove[1];
        this.buffMgr.useBuffs();
        this.buffMgr.cullInactiveBuffs();
        this.updatePositionAgain();
    }

    public boolean tryWarp(final int x, final int y) {
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, y);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
        try {
            final MapObject there = this.dungeon.getCellOffset(
                    this.playerLocation, offset);
            if (!there.isSolid()) {
                return true;
            } else {
                return false;
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    public void warp(final int x, final int y) {
        this.moveX = x;
        this.moveY = y;
        this.updatePositionAgain();
    }

    public void updatePositionAgain() {
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, this.moveY);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, this.moveX);
        try {
            final MapObject there = this.dungeon.getCellOffset(
                    this.playerLocation, offset);
            if (!there.isSolid()) {
                final MapObject player = this.dungeon
                        .getCell(this.playerLocation);
                this.dungeon.setCell(this.savedMapObject, this.playerLocation);
                this.playerLocation.setLocation(
                        NDimensionalMap.ROW_DIMENSION,
                        this.playerLocation
                                .getLocation(NDimensionalMap.ROW_DIMENSION)
                                + this.moveY);
                this.playerLocation.setLocation(
                        NDimensionalMap.COLUMN_DIMENSION,
                        this.playerLocation
                                .getLocation(NDimensionalMap.COLUMN_DIMENSION)
                                + this.moveX);
                this.savedMapObject = (DungeonObject) there;
                this.dungeon.setCell(player, this.playerLocation);
                final Point newViewingWindowOffset = new Point(this.moveY,
                        this.moveX);
                this.dungeon.alterViewingWindowOffset(newViewingWindowOffset);
                this.redrawDungeon();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void updateMonsterPosition(final int xMove, final int yMove,
            final int xLoc, final int yLoc, final Monster monster) {
        final NDimensionalLocation loc = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        loc.setLocation(NDimensionalMap.ROW_DIMENSION, yLoc);
        loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, xLoc);
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, yMove);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, xMove);
        try {
            final MapObject there = this.dungeon.getCellOffset(loc, offset);
            if (!there.isSolid() && !there.getName().equals("Monster")) {
                this.dungeon.setCell(monster.getSavedObject(), loc);
                loc.setLocation(NDimensionalMap.ROW_DIMENSION,
                        loc.getLocation(NDimensionalMap.ROW_DIMENSION) + yMove);
                loc.setLocation(NDimensionalMap.COLUMN_DIMENSION,
                        loc.getLocation(NDimensionalMap.COLUMN_DIMENSION)
                                + xMove);
                if (there.getName().equals("Player")) {
                    DungeonDiver.getHoldingBag().getBattle().doBattle();
                    this.postBattle(loc, monster, false);
                    this.dungeon.setCell(there, loc);
                } else {
                    // Move the monster
                    monster.setSavedObject((DungeonObject) there);
                    this.dungeon.setCell(monster, loc);
                    // Is the current object ice?
                    if (there.getName().equals("Ice")) {
                        // Yes it is - is ice enabled?
                        if (DungeonDiver.getHoldingBag().getPrefs()
                                .getPreferenceValue(Preferences.ICE_ENABLED)) {
                            // Yes it is - move the monster again
                            this.updateMonsterPosition(xMove, yMove, xLoc
                                    + xMove, yLoc + yMove, monster);
                        }
                    }
                }
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    private void redrawDungeon() {
        // Draw the dungeon, if it is visible
        if (this.outputFrame.isVisible()) {
            this.borderPane.removeAll();
            this.outputPane = this.dungeon.drawGameWithVisibility(null);
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
            this.outputFrame.pack();
            this.showDungeon();
            this.updateEffects();
            this.updateStats();
        }
    }

    public void redrawDungeonBypassHook() {
        // Draw the dungeon, if it is visible
        if (this.outputFrame.isVisible()) {
            this.borderPane.removeAll();
            this.outputPane = this.dungeon
                    .drawGameWithVisibilityBypassHook(null);
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
            this.outputFrame.pack();
            this.showDungeon();
            this.updateEffects();
            this.updateStats();
        }
    }

    public JFrame getOutputFrame() {
        if (this.outputFrame != null && this.outputFrame.isVisible()) {
            return this.outputFrame;
        } else {
            return null;
        }
    }

    public void newDungeon() {
        this.generatorTask = new Thread(new NewDungeonTask());
        this.generatorTask.start();
    }

    public void newScheme() {
        this.generatorTask = new Thread(new NewSchemeTask());
        this.generatorTask.start();
    }

    public void newDungeonAndScheme() {
        this.generatorTask = new Thread(new NewDungeonAndSchemeTask());
        this.generatorTask.start();
    }

    public void exploreDungeon() {
        this.showDungeon();
        this.redrawDungeonBypassHook();
    }

    public boolean doesDungeonExist() {
        return this.dungeon != null;
    }

    public void showDungeon() {
        try {
            if (!this.generatorTask.isAlive() && !Battle.isInBattle()) {
                this.outputFrame.setVisible(true);
                this.statsFrame.setVisible(true);
            }
        } catch (final NullPointerException np) {
            this.outputFrame.setVisible(true);
            this.statsFrame.setVisible(true);
        }
    }

    void showDungeonImmediately() {
        if (!Battle.isInBattle()) {
            this.outputFrame.setVisible(true);
            this.statsFrame.setVisible(true);
        }
    }

    public void hideDungeon() {
        this.outputFrame.setVisible(false);
        this.statsFrame.setVisible(false);
    }

    public NDimensionalLocation getPlayerLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(final NDimensionalLocation loc) {
        this.playerLocation = loc;
    }

    private void updateEffects() {
        this.effectsPane = this.buffMgr.updateEffects();
        this.outputFrame.pack();
    }

    private void updateStats() {
        int x;
        final String[] s = Stats.getStatString();
        for (x = 0; x < Stats.MAX_STATS; x++) {
            this.stats[x].setText(s[x]);
        }
        this.statsFrame.pack();
    }

    int getDefaultDrawDistance() {
        return DungeonGUI.DEFAULT_DRAW_DISTANCE;
    }

    void setDefaultDrawDistance() {
        this.dungeon.setDrawDistance(DungeonGUI.DEFAULT_DRAW_DISTANCE);
    }

    public void increaseVisibility() {
        if (this.dungeon.getDrawDistance() < DungeonGUI.MAX_DRAW_DISTANCE) {
            this.dungeon.incrementDrawDistance();
        }
    }

    public void decreaseVisibility() {
        if (this.dungeon.getDrawDistance() > DungeonGUI.MIN_DRAW_DISTANCE) {
            this.dungeon.decrementDrawDistance();
        }
    }

    public void decay() {
        final Tile tile = (Tile) DungeonObjectList.getSpecificObject("Tile");
        this.savedMapObject = tile;
    }

    public void postBattle(final NDimensionalLocation loc, final Monster m,
            final boolean player) {
        final DungeonObject saved = m.getSavedObject();
        if (player) {
            this.savedMapObject = saved;
        } else {
            this.dungeon.setCell(saved, loc);
        }
        this.dungeon.generateOneMonster();
    }

    Dimension getViewingWindow() {
        return this.viewingWindow;
    }

    public int getViewingWindowSize() {
        return DungeonGUI.VIEWING_WINDOW_SIZE;
    }

    int computeDungeonSize() {
        final int dl = DungeonDiver.getHoldingBag().getPlayer()
                .getDungeonLevel();
        return DungeonGUI.DUNGEON_SIZE + (dl - 1)
                * DungeonGUI.DUNGEON_SIZE_INCREMENT;
    }
}