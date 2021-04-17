package studio.ignitionigloogames.dungeondiver1.dungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.Preferences;
import studio.ignitionigloogames.dungeondiver1.creatures.Battle;
import studio.ignitionigloogames.dungeondiver1.creatures.Stats;
import studio.ignitionigloogames.dungeondiver1.dungeon.buffs.DungeonBuffManager;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.DungeonObject;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.DungeonObjectList;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.Monster;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.Tile;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.Wall;
import studio.ignitionigloogames.dungeondiver1.gui.MainWindow;
import studio.ignitionigloogames.dungeondiver1.utilities.MapObject;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalLocation;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;

public class DungeonGUI {
    // Fields
    private final JPanel outputPane, effectsPane, borderPane, statsPane,
            generatorPane;
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
    private static final int MAX_DRAW_DISTANCE = DungeonGUI.VIEWING_WINDOW_SIZE
            / 2 + 1;
    private static final int MIN_DRAW_DISTANCE = 2;
    private static final int DEFAULT_DRAW_DISTANCE = 4;
    private static final int DUNGEON_SIZE = 30;
    private static final int DUNGEON_SIZE_INCREMENT = 2;

    // Constructors
    public DungeonGUI() {
        this.handler = new DungeonGUIEventHandler();
        this.generatorPane = new JPanel();
        this.generatorProgress = new JProgressBar();
        this.generatorProgress.setIndeterminate(true);
        this.generatorPane.add(this.generatorProgress);
        this.objectList = new DungeonObjectList();
        this.savedMapObject = (Tile) DungeonObjectList
                .getSpecificObject("Tile");
        this.effectsPane = new JPanel();
        this.buffMgr = new DungeonBuffManager(this.effectsPane);
        this.outputPane = new JPanel();
        this.borderPane = new JPanel();
        this.statsPane = new JPanel();
        this.borderPane.setLayout(new BorderLayout());
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
        this.borderPane.add(this.statsPane, BorderLayout.EAST);
        this.statsPane.setLayout(new GridLayout(Stats.MAX_STATS, 1));
        this.stats = new JLabel[Stats.MAX_STATS];
        int x;
        for (x = 0; x < Stats.MAX_STATS; x++) {
            this.stats[x] = new JLabel("");
            this.stats[x].setOpaque(true);
            this.statsPane.add(this.stats[x]);
        }
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

    public void generateModeOn() {
        MainWindow main = MainWindow.getMainWindow();
        main.attachAndSave(this.generatorPane);
    }

    public void generateModeOff() {
        MainWindow main = MainWindow.getMainWindow();
        main.restoreSaved();
        main.addKeyListener(this.handler);
        main.addWindowListener(this.handler);
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
        Wall fallback = new Wall();
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, y);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
        final MapObject there = this.dungeon.getCellOffset(this.playerLocation,
                offset, fallback);
        return !there.isSolid();
    }

    public void warp(final int x, final int y) {
        this.moveX = x;
        this.moveY = y;
        this.updatePositionAgain();
    }

    public void updatePositionAgain() {
        Wall fallback = new Wall();
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, this.moveY);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, this.moveX);
        final MapObject there = this.dungeon.getCellOffset(this.playerLocation,
                offset, fallback);
        if (!there.isSolid()) {
            final MapObject player = this.dungeon.getCell(this.playerLocation);
            this.dungeon.setCell(this.savedMapObject, this.playerLocation);
            this.playerLocation
                    .setLocation(NDimensionalMap.ROW_DIMENSION,
                            this.playerLocation
                                    .getLocation(NDimensionalMap.ROW_DIMENSION)
                                    + this.moveY);
            this.playerLocation
                    .setLocation(NDimensionalMap.COLUMN_DIMENSION,
                            this.playerLocation.getLocation(
                                    NDimensionalMap.COLUMN_DIMENSION)
                                    + this.moveX);
            this.savedMapObject = (DungeonObject) there;
            this.dungeon.setCell(player, this.playerLocation);
            final Point newViewingWindowOffset = new Point(this.moveY,
                    this.moveX);
            this.dungeon.alterViewingWindowOffset(newViewingWindowOffset);
            this.redrawDungeon();
        }
    }

    public void updateMonsterPosition(final int xMove, final int yMove,
            final int xLoc, final int yLoc, final Monster monster) {
        Wall fallback = new Wall();
        final NDimensionalLocation loc = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        loc.setLocation(NDimensionalMap.ROW_DIMENSION, yLoc);
        loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, xLoc);
        final NDimensionalLocation offset = new NDimensionalLocation(
                this.dungeon.getMaxDimension());
        offset.setLocation(NDimensionalMap.ROW_DIMENSION, yMove);
        offset.setLocation(NDimensionalMap.COLUMN_DIMENSION, xMove);
        final MapObject there = this.dungeon.getCellOffset(loc, offset,
                fallback);
        if (!there.isSolid() && !there.getName().equals("Monster")) {
            this.dungeon.setCell(monster.getSavedObject(), loc);
            loc.setLocation(NDimensionalMap.ROW_DIMENSION,
                    loc.getLocation(NDimensionalMap.ROW_DIMENSION) + yMove);
            loc.setLocation(NDimensionalMap.COLUMN_DIMENSION,
                    loc.getLocation(NDimensionalMap.COLUMN_DIMENSION) + xMove);
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
                        this.updateMonsterPosition(xMove, yMove, xLoc + xMove,
                                yLoc + yMove, monster);
                    }
                }
            }
        }
    }

    private void redrawDungeon() {
        // Draw the dungeon, if it is visible
        this.borderPane.removeAll();
        this.dungeon.drawGameWithVisibility(null);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
        this.updateEffects();
        this.updateStats();
    }

    public void redrawDungeonBypassHook() {
        // Draw the dungeon, if it is visible
        this.borderPane.removeAll();
        this.dungeon.drawGameWithVisibilityBypassHook(null);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.effectsPane, BorderLayout.SOUTH);
        this.updateEffects();
        this.updateStats();
    }

    public void newDungeon() {
        this.generatorTask = new Thread(new NewDungeonTask(this.outputPane));
        this.generatorTask.start();
    }

    public void newScheme() {
        this.generatorTask = new Thread(new NewSchemeTask());
        this.generatorTask.start();
    }

    public void newDungeonAndScheme() {
        this.generatorTask = new Thread(
                new NewDungeonAndSchemeTask(this.outputPane));
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
        if (this.generatorTask != null) {
            if (!this.generatorTask.isAlive()) {
                this.showDungeonImmediately();
            }
        }
    }

    void showDungeonImmediately() {
        if (!Battle.isInBattle()) {
            MainWindow main = MainWindow.getMainWindow();
            main.attachAndSave(this.borderPane);
            main.addKeyListener(this.handler);
            main.addWindowListener(this.handler);
        }
    }

    public NDimensionalLocation getPlayerLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(final NDimensionalLocation loc) {
        this.playerLocation = loc;
    }

    private void updateEffects() {
        this.buffMgr.updateEffects();
    }

    private void updateStats() {
        int x;
        final String[] s = Stats.getStatString();
        for (x = 0; x < Stats.MAX_STATS; x++) {
            this.stats[x].setText(s[x]);
        }
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
        return DungeonGUI.DUNGEON_SIZE
                + (dl - 1) * DungeonGUI.DUNGEON_SIZE_INCREMENT;
    }
}