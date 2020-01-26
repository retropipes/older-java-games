/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.game;

import javax.swing.JFrame;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.GenerateTask;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.effects.DungeonEffectManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Empty;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.EmptyVoid;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ImageTransformer;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public final class GameLogicManager {
    // Fields
    private boolean savedGameFlag;
    private final GameViewingWindowManager vwMgr;
    private boolean stateChanged;
    private final GameGUIManager gui;
    private final DungeonEffectManager em;
    private final MovementTask mt;
    private int stoneCount;

    // Constructors
    public GameLogicManager() {
        this.vwMgr = new GameViewingWindowManager();
        this.em = new DungeonEffectManager();
        this.gui = new GameGUIManager();
        this.mt = new MovementTask(this.vwMgr, this.em, this.gui);
        this.mt.start();
        this.savedGameFlag = false;
        this.stateChanged = true;
        this.stoneCount = 0;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = DynamicDungeon.getApplication().getOutputFrame();
        this.em.deactivateAllEffects();
        if (this.savedGameFlag) {
            if (PartyManager.getParty() != null) {
                return true;
            } else {
                return PartyManager.createParty(owner);
            }
        } else {
            return PartyManager.createParty(owner);
        }
    }

    String getPowerString() {
        final int curr = DynamicDungeon.getApplication().getDungeonManager()
                .getDungeon().getStones() * 100;
        final int max = this.stoneCount;
        return "Power: " + curr / max + "%";
    }

    public void updateStoneCount() {
        this.stoneCount = DynamicDungeon.getApplication().getDungeonManager()
                .getDungeon().getStoneCount();
    }

    public void enableEvents() {
        this.mt.fireStepActions();
        this.gui.enableEvents();
    }

    public void disableEvents() {
        this.gui.disableEvents();
    }

    public void stopMovement() {
        this.mt.stopMovement();
    }

    public void deactivateAllEffects() {
        this.em.deactivateAllEffects();
    }

    public void viewingWindowSizeChanged() {
        this.gui.viewingWindowSizeChanged(this.em);
        this.resetViewingWindow();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void activateEffect(final int effectID) {
        this.em.activateEffect(effectID);
    }

    public void setStatusMessage(final String msg) {
        this.gui.setStatusMessage(msg);
    }

    public void updatePositionRelative(final int dirX, final int dirY,
            final int dirZ) {
        this.mt.moveRelative(dirX, dirY, dirZ);
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        return this.mt.tryAbsolute(x, y, z);
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        this.mt.moveAbsolute(x, y, z);
    }

    public void redrawDungeon() {
        this.gui.redrawDungeon();
    }

    public void resetViewingWindowAndPlayerLocation() {
        GameLogicManager.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null && this.vwMgr != null) {
            this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                    - GameViewingWindowManager.getOffsetFactorX());
            this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                    - GameViewingWindowManager.getOffsetFactorY());
        }
    }

    public static void resetPlayerLocation() {
        GameLogicManager.resetPlayerLocation(0);
    }

    public static void resetPlayerLocation(final int level) {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void checkStoneCount() {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m.getStones() == this.stoneCount) {
            SoundManager.playSound(SoundConstants.SOUND_FINISH);
            app.showMessage("The way forward is now open!");
            m.fullScanExit();
        }
    }

    public void goToLevelOffset(final int level) {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final boolean levelExists = m.doesLevelExistOffset(level);
        this.stopMovement();
        if (levelExists) {
            new LevelLoadTask(level).start();
        } else {
            new GenerateTask(false).start();
        }
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        // Restore the maze
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getDungeonManager().setLoaded(false);
        }
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getDungeonManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public static void decay() {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(new Empty(), m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), DungeonConstants.LAYER_OBJECT);
    }

    public static void morph(final AbstractDungeonObject morphInto) {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(morphInto, m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), morphInto.getLayer());
    }

    public void keepNextMessage() {
        this.gui.keepNextMessage();
    }

    public void identifyObject(final int x, final int y) {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - GameViewingWindowManager.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - GameViewingWindowManager.getOffsetFactorY();
        final int destX = x / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = m.getPlayerLocationZ();
        try {
            final AbstractDungeonObject target1 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_GROUND);
            final AbstractDungeonObject target2 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            DynamicDungeon.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            DynamicDungeon.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        }
    }

    public void playDungeon() {
        final Application app = DynamicDungeon.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (app.getDungeonManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getDungeonManager().getDungeon().switchLevel(
                        app.getDungeonManager().getDungeon().getStartLevel());
                this.updateStoneCount();
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.updateGameGUI(this.em);
            // Make sure initial area player is in is visible
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            m.updateVisibleSquares(px, py, pz);
            this.showOutput();
            this.redrawDungeon();
        } else {
            CommonDialogs.showDialog("No Dungeon Opened");
        }
    }

    public void showOutput() {
        DynamicDungeon.getApplication().setMode(Application.STATUS_GAME);
        this.gui.showOutput();
    }

    public void hideOutput() {
        this.stopMovement();
        this.gui.hideOutput();
    }
}
