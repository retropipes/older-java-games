/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.game;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.Application;
import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.game.scripts.InternalScriptRunner;
import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericCharacter;
import com.puttysoftware.dungeondiver3.support.map.generic.InfiniteRecursionException;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.map.generic.TypeConstants;
import com.puttysoftware.dungeondiver3.support.map.objects.Empty;
import com.puttysoftware.dungeondiver3.support.map.objects.EmptyVoid;
import com.puttysoftware.dungeondiver3.support.map.objects.Player;
import com.puttysoftware.dungeondiver3.support.map.objects.Tile;
import com.puttysoftware.dungeondiver3.support.map.objects.Wall;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.SoundManager;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptArea;

public class GameLogic {
    // Fields
    private MapObject savedMapObject;
    private boolean savedGameFlag;
    private final ScoreTracker st;
    private boolean stateChanged;
    private final GameGUI gameGUI;

    // Constructors
    public GameLogic() {
        this.gameGUI = new GameGUI();
        this.st = new ScoreTracker();
        this.savedMapObject = new Empty();
        this.savedGameFlag = false;
        this.stateChanged = true;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = DungeonDiver3.getApplication().getOutputFrame();
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

    private GameViewingWindowManager getViewManager() {
        return this.gameGUI.getViewManager();
    }

    public void addToScore(final long points) {
        this.st.addToScore(points);
    }

    public void showCurrentScore() {
        this.st.showCurrentScore();
    }

    private static void fireStepActions(final int x, final int y, final int z) {
        PartyManager.getParty().fireStepActions();
        DungeonDiver3.getApplication().getScenarioManager().getMap()
                .updateVisibleSquares(x, y, z);
    }

    public void updateStats() {
        this.gameGUI.updateStats();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void setStatusMessage(final String msg) {
        this.gameGUI.setStatusMessage(msg);
    }

    public void updatePositionRelative(final int x, final int y, final int z) {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        final Application app = DungeonDiver3.getApplication();
        boolean proceed = false;
        MapObject o = null;
        MapObject groundInto = new Empty();
        MapObject below = null;
        MapObject nextBelow = null;
        MapObject nextAbove = null;
        try {
            try {
                o = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Empty();
            }
            try {
                below = m.getCell(px, py, pz, MapConstants.LAYER_GROUND);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                below = new Empty();
            }
            try {
                nextBelow = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_GROUND);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                nextBelow = new Empty();
            }
            try {
                nextAbove = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                nextAbove = new Wall();
            }
            try {
                proceed = o.preMoveCheck(true, px + x, py + y, pz + z, m);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                proceed = true;
            } catch (final InfiniteRecursionException ir) {
                proceed = false;
            }
        } catch (final NullPointerException np) {
            proceed = false;
            o = new Empty();
        }
        if (proceed) {
            m.savePlayerLocation();
            this.getViewManager().saveViewingWindow();
            try {
                if (GameLogic.checkSolid(pz + z, this.savedMapObject, below,
                        nextBelow, nextAbove)) {
                    m.setCell(this.savedMapObject, px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    m.offsetPlayerLocationX(x);
                    m.offsetPlayerLocationY(y);
                    m.offsetPlayerLocationZ(z);
                    px += x;
                    py += y;
                    pz += z;
                    this.getViewManager().offsetViewingWindowLocationX(y);
                    this.getViewManager().offsetViewingWindowLocationY(x);
                    this.savedMapObject = m.getCell(px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    m.setCell(new Player(), px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    app.getScenarioManager().setDirty(true);
                    this.redrawMap();
                    groundInto = m.getCell(px, py, pz,
                            MapConstants.LAYER_GROUND);
                    if (groundInto.overridesDefaultPostMove()) {
                        InternalScriptRunner.runScript(groundInto
                                .getPostMoveScript(false, px, py, pz));
                        if (!this.savedMapObject
                                .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                            InternalScriptRunner.runScript(this.savedMapObject
                                    .getPostMoveScript(false, px, py, pz));
                        }
                    } else {
                        InternalScriptRunner.runScript(this.savedMapObject
                                .getPostMoveScript(false, px, py, pz));
                    }
                } else {
                    // Move failed - object is solid in that direction
                    GameLogic.fireMoveFailedActions(px + x, py + y, pz + z,
                            this.savedMapObject, below, nextBelow, nextAbove);
                    proceed = false;
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.getViewManager().restoreViewingWindow();
                m.restorePlayerLocation();
                m.setCell(new Player(), m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                // Move failed - attempted to go outside the map
                DungeonDiver3.getApplication().showMessage("Can't go that way");
                o = new Empty();
                proceed = false;
            }
        } else {
            // Move failed - pre-move check failed
            InternalScriptRunner.runScript(MapObject.getMoveFailedScript(false,
                    px + x, py + y, pz + z));
            proceed = false;
        }
        if (redrawsSuspended) {
            // Redraw post-suspend
            this.redrawMap();
            redrawsSuspended = false;
        }
        // Post-move actions
        final ArrayList<InternalScriptArea> areaScripts = app
                .getScenarioManager().getMap()
                .getScriptAreasAtPoint(new Point(px, py), pz);
        for (final InternalScriptArea isa : areaScripts) {
            InternalScriptRunner.runScript(isa);
        }
        GameLogic.fireStepActions(x, y, z);
        this.updateStats();
        this.checkGameOver();
    }

    public void updatePositionRelativeNoEvents(final int x, final int y,
            final int z) {
        boolean redrawsSuspended = false;
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        final Application app = DungeonDiver3.getApplication();
        boolean proceed = false;
        MapObject o = null;
        MapObject below = null;
        MapObject nextBelow = null;
        MapObject nextAbove = null;
        try {
            try {
                o = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Empty();
            }
            try {
                below = m.getCell(px, py, pz, MapConstants.LAYER_GROUND);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                below = new Empty();
            }
            try {
                nextBelow = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_GROUND);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                nextBelow = new Empty();
            }
            try {
                nextAbove = m.getCell(px + x, py + y, pz + z,
                        MapConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                nextAbove = new Wall();
            }
            try {
                proceed = o.preMoveCheck(true, px + x, py + y, pz + z, m);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                proceed = true;
            } catch (final InfiniteRecursionException ir) {
                proceed = false;
            }
        } catch (final NullPointerException np) {
            proceed = false;
            o = new Empty();
        }
        if (proceed) {
            m.savePlayerLocation();
            this.getViewManager().saveViewingWindow();
            try {
                if (GameLogic.checkSolid(z + pz, this.savedMapObject, below,
                        nextBelow, nextAbove)) {
                    m.setCell(this.savedMapObject, px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    m.offsetPlayerLocationX(x);
                    m.offsetPlayerLocationY(y);
                    m.offsetPlayerLocationZ(z);
                    px += x;
                    py += y;
                    pz += z;
                    this.getViewManager().offsetViewingWindowLocationX(y);
                    this.getViewManager().offsetViewingWindowLocationY(x);
                    this.savedMapObject = m.getCell(px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    m.setCell(new Player(), px, py, pz,
                            MapConstants.LAYER_OBJECT);
                    app.getScenarioManager().setDirty(true);
                    this.redrawMap();
                } else {
                    // Move failed - object is solid in that direction
                    GameLogic.fireMoveFailedActions(px + x, py + y, pz + z,
                            this.savedMapObject, below, nextBelow, nextAbove);
                    proceed = false;
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.getViewManager().restoreViewingWindow();
                m.restorePlayerLocation();
                m.setCell(new Player(), m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                // Move failed - attempted to go outside the map
                DungeonDiver3.getApplication().showMessage("Can't go that way");
                o = new Empty();
                proceed = false;
            }
        } else {
            // Move failed - pre-move check failed
            InternalScriptRunner.runScript(MapObject.getMoveFailedScript(false,
                    px + x, py + y, pz + z));
            proceed = false;
        }
        if (redrawsSuspended) {
            // Redraw post-suspend
            this.redrawMap();
            redrawsSuspended = false;
        }
        this.updateStats();
        this.checkGameOver();
    }

    private void saveSavedMapObject() {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) m.getCell(px, py, pz,
                MapConstants.LAYER_OBJECT);
        player.setSavedObject(this.savedMapObject);
    }

    private void restoreSavedMapObject() {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) m.getCell(px, py, pz,
                MapConstants.LAYER_OBJECT);
        this.savedMapObject = player.getSavedObject();
    }

    private void findPlayerAndAdjust() {
        // Find the player, adjust player location
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        m.findStart();
        m.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), m.getActiveLevelNumber());
        this.resetViewingWindow();
        this.redrawMap();
    }

    private static boolean checkSolid(final int z, final MapObject inside,
            final MapObject below, final MapObject nextBelow,
            final MapObject nextAbove) {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        return !(insideSolid || belowSolid || nextBelowSolid || nextAboveSolid);
    }

    private static void fireMoveFailedActions(final int x, final int y,
            final int z, final MapObject inside, final MapObject below,
            final MapObject nextBelow, final MapObject nextAbove) {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        if (insideSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (belowSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (nextBelowSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (nextAboveSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        try {
            m.getCell(x, y, z, MapConstants.LAYER_OBJECT).preMoveCheck(true, x,
                    y, z, m);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        m.savePlayerLocation();
        this.getViewManager().saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MapConstants.LAYER_OBJECT)
                    .isConditionallySolid(m, z)) {
                m.setCell(this.savedMapObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setPlayerLocation(x, y, z, 0);
                this.getViewManager()
                        .setViewingWindowLocationX(m.getPlayerLocationY()
                                - GameViewingWindowManager.getOffsetFactor());
                this.getViewManager()
                        .setViewingWindowLocationY(m.getPlayerLocationX()
                                - GameViewingWindowManager.getOffsetFactor());
                this.savedMapObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setCell(new Player(), m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.redrawMap();
                app.getScenarioManager().setDirty(true);
                InternalScriptRunner.runScript(
                        this.savedMapObject.getPostMoveScript(false, x, y, z));
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(new Player(), m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MapConstants.LAYER_OBJECT);
            DungeonDiver3.getApplication()
                    .showMessage("Can't go outside the map");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(new Player(), m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MapConstants.LAYER_OBJECT);
            DungeonDiver3.getApplication()
                    .showMessage("Can't go outside the map");
        }
        GameLogic.fireStepActions(x, y, z);
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z) {
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        m.savePlayerLocation();
        this.getViewManager().saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MapConstants.LAYER_OBJECT)
                    .isConditionallySolid(m, z)) {
                m.setCell(this.savedMapObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setPlayerLocation(x, y, z, m.getPlayerLocationW());
                this.getViewManager()
                        .setViewingWindowLocationX(m.getPlayerLocationY()
                                - GameViewingWindowManager.getOffsetFactor());
                this.getViewManager()
                        .setViewingWindowLocationY(m.getPlayerLocationX()
                                - GameViewingWindowManager.getOffsetFactor());
                this.savedMapObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setCell(new Player(), m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.redrawMap();
                app.getScenarioManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(new Player(), m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MapConstants.LAYER_OBJECT);
            DungeonDiver3.getApplication()
                    .showMessage("Can't go outside the map");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(new Player(), m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MapConstants.LAYER_OBJECT);
            DungeonDiver3.getApplication()
                    .showMessage("Can't go outside the map");
        }
    }

    public void goToLevelRelative(final int level) {
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        final boolean levelExists = m.doesLevelExistOffset(level);
        if (!levelExists && m.isLevelOffsetValid(level)) {
            // Create the level
            this.saveSavedMapObject();
            m.addLevel(Support.getGameMapSize(), Support.getGameMapSize(),
                    Support.getGameMapFloorSize());
            m.fillLevelRandomly(new Tile(), new Empty());
            m.save();
            this.findPlayerAndAdjust();
            this.restoreSavedMapObject();
        } else if (levelExists && m.isLevelOffsetValid(level)) {
            this.saveSavedMapObject();
            m.switchLevelOffset(level);
            this.findPlayerAndAdjust();
            this.restoreSavedMapObject();
        }
    }

    public void redrawMap() {
        this.gameGUI.redrawMap();
    }

    private void resetViewingWindowAndPlayerLocation() {
        GameLogic.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        this.gameGUI.resetViewingWindow();
    }

    private static void resetPlayerLocation() {
        GameLogic.resetPlayerLocation(0);
    }

    private static void resetPlayerLocation(final int level) {
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        m.switchLevel(level);
        m.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), level);
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        // Restore the map
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        } // Reset saved game flag
        this.savedGameFlag = false;
        app.getScenarioManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public void checkGameOver() {
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    private void gameOver() {
        SoundManager.playSound(GameSoundConstants.SOUND_PARTY_SLAIN);
        CommonDialogs.showDialog("You have died - Game Over!");
        this.st.commitScore();
        this.exitGame();
    }

    public JFrame getOutputFrame() {
        return this.gameGUI.getOutputFrame();
    }

    public void decay() {
        this.savedMapObject = new Empty();
    }

    void identifyObject(final int x, final int y) {
        final Application app = DungeonDiver3.getApplication();
        final Map m = app.getScenarioManager().getMap();
        final int xOffset = this.getViewManager().getViewingWindowLocationX()
                - GameViewingWindowManager.getOffsetFactor();
        final int yOffset = this.getViewManager().getViewingWindowLocationY()
                - GameViewingWindowManager.getOffsetFactor();
        final int destX = x / ImageManager.getGraphicSize()
                + this.getViewManager().getViewingWindowLocationX() - xOffset
                + yOffset;
        final int destY = y / ImageManager.getGraphicSize()
                + this.getViewManager().getViewingWindowLocationY() + xOffset
                - yOffset;
        final int destZ = m.getPlayerLocationZ();
        try {
            final MapObject target1 = m.getCell(destX, destY, destZ,
                    MapConstants.LAYER_GROUND);
            final MapObject target2 = m.getCell(destX, destY, destZ,
                    MapConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ, m);
            target2.determineCurrentAppearance(destX, destY, destZ, m);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            DungeonDiver3.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(GameSoundConstants.SOUND_SCAN);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ, m);
            DungeonDiver3.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(GameSoundConstants.SOUND_SCAN);
        }
    }

    public void playMap() {
        final Application app = DungeonDiver3.getApplication();
        app.getGUIManager().hideGUI();
        app.setInGame();
        if (this.stateChanged) {
            // Initialize only if the map state has changed
            boolean didMapExist = true;
            final int currRandom = PreferencesManager.getGeneratorRandomness();
            if (app.getScenarioManager().getMap() == null) {
                didMapExist = false;
            }
            DungeonDiver3.newScenario();
            app.getScenarioManager().setMap(new Map());
            app.getScenarioManager().getMap().createMaps();
            app.getScenarioManager().getMap().addLevel(Support.getGameMapSize(),
                    Support.getGameMapSize(), Support.getGameMapFloorSize());
            app.getScenarioManager().getMap().fillLevelRandomly(new Tile(),
                    new Empty());
            app.getScenarioManager().getMap().setPlayerLocationW(0);
            app.getScenarioManager().getMap().findStart();
            app.getScenarioManager().getMap().save();
            if (didMapExist) {
                app.getScenarioManager().getMap().setGeneratorRandomness(
                        currRandom, DungeonDiver3.GENERATOR_RANDOMNESS_MAX);
            }
            this.resetViewingWindowAndPlayerLocation();
            this.savedMapObject = new Empty();
            this.stateChanged = false;
        }
        // Make sure message area is attached to the border pane
        this.gameGUI.updateGameGUI();
        this.showOutput();
        this.checkGameOver();
        this.redrawMap();
    }

    public void showOutput() {
        this.gameGUI.showOutput();
    }

    public void hideOutput() {
        this.gameGUI.hideOutput();
    }
}
